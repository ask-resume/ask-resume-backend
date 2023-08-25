package app.askresume.scheduler.job

import app.askresume.domain.generative.factory.GenerativeCommandFactory
import app.askresume.domain.prompt.service.PromptReadOnlyService
import app.askresume.domain.result.service.ResultService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.service.SubmitCommandService
import app.askresume.domain.submit.service.SubmitDataCommandService
import app.askresume.domain.submit.service.SubmitReadOnlyService
import app.askresume.external.openai.dto.ChatCompletionsMessageResponse
import app.askresume.external.openai.mapper.OpenAiMapper
import app.askresume.external.openai.service.OpenAiService
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenerativeModelJob(
    private val submitCommandService: SubmitCommandService,
    private val submitReadOnlyService: SubmitReadOnlyService,
    private val submitDataCommandService: SubmitDataCommandService,
    private val resultService: ResultService,
    private val promptReadOnlyService: PromptReadOnlyService,
    private val generativeCommandFactory: GenerativeCommandFactory,

    private val openAiMapper: OpenAiMapper,
    private val openAiService: OpenAiService,
) {
    val log = logger()

    @Transactional
    fun execute() {
        submitReadOnlyService.findRequestedFirstSubmit()?.run {

            // 객체 분리
            val (submitId, submitDataId, serviceType, parameter) = this

            // 프롬프트 호출
            val prompt = promptReadOnlyService.findPromptAndFormatting(
                serviceType = serviceType,
                parameter = parameter
            )

            // 데이터 mapping 진행
            val createdChatCompletionsRequest = openAiMapper.promptAndContentToChatCompletionsRequest(
                serviceType = serviceType,
                prompt = prompt,
                content = parameter["content"] as String
            )

            try {
                // 데이터로 요청
                val response = openAiService.requestOpenAiChatCompletion(createdChatCompletionsRequest)
                generativeModelJobSuccess(
                    submitId = submitId,
                    submitDataId = submitDataId,
                    serviceType = serviceType,
                    response = response,
                )
            } catch (e: Exception) {
                log.error(OPEN_AI_ERROR_MESSAGE, e.javaClass.simpleName, e.message, e)

                generativeModelJobFail(
                    submitId = submitId,
                    submitDataId = submitDataId,
                )
            }
        } ?: run {
            log.info("submit data가 없어, sleep 진행.")
            Thread.sleep(SLEEP_TIME)
        }
    }

    @Transactional
    fun generativeModelJobSuccess(
        submitId: Long,
        submitDataId: Long,
        serviceType: ServiceType,
        response: ChatCompletionsMessageResponse,
    ) {

        // 결과 저장
        val generativeService = generativeCommandFactory.createGenerativeProvider(serviceType)
        generativeService.saveGenerativeResult(
            submitId = submitId,
            submitDataId = submitDataId,
            choices = response.choices
        )

        // Result 데이터 저장 (토큰량)
        resultService.saveResultResponseData(
            model = response.model,
            created = response.created,
            promptTokens = response.usage.promptTokens,
            contentToken = response.usage.completionTokens,
            totalTokens = response.usage.totalTokens
        )

        // 성공으로 업데이트
        submitDataCommandService.updateStatus(
            submitDataId = submitDataId,
            changeStatus = SubmitDataStatus.SUCCESS,
        )

        // 아이템 개수 체크 후 Completed로 변경 해야함
        submitCommandService.verifyAllSubmittedDataSuccess(submitId = submitId)
    }

    @Transactional
    fun generativeModelJobFail(
        submitId: Long,
        submitDataId: Long,
    ) {
        submitDataCommandService.updateStatus(
            submitDataId = submitDataId,
            changeStatus = SubmitDataStatus.RESEND,
        )

        // 횟수 1 플러스하고, 체크
        submitCommandService.increaseAttemptsAndCheckFailure(submitId = submitId)
    }

    companion object {
        private const val SLEEP_TIME: Long = 1_000 * 10 // 1000ms * 10 = 10s

        private const val OPEN_AI_ERROR_MESSAGE = "[ERROR] {}, : {}"

    }
}