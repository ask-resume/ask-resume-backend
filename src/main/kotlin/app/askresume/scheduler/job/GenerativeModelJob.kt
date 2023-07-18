package app.askresume.scheduler.job

import app.askresume.domain.generative.factory.GenerativeFactory
import app.askresume.domain.result.service.ResultService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.service.SubmitDataService
import app.askresume.domain.submit.service.SubmitQueryService
import app.askresume.domain.submit.service.SubmitService
import app.askresume.external.openai.dto.ChatCompletionsMessageResponse
import app.askresume.external.openai.service.OpenAiService
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class GenerativeModelJob(
    private val submitService: SubmitService,
    private val submitQueryService: SubmitQueryService,
    private val submitDataService: SubmitDataService,
    private val openAiService: OpenAiService,
    private val resultService: ResultService,
    private val generativeFactory: GenerativeFactory,
) {
    val log = logger()

    @Transactional
    fun execute() {
        submitQueryService.findRequestedFirstSubmit()?.run {

            // 객체 분리
            val (submitId, submitDataId, serviceType, parameter) = this

            // 프롬프트 호출
            val createdChatCompletionsRequest = openAiService.createdChatCompletionsRequest(
                serviceType = serviceType,
                parameter = parameter,
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
                generativeModelJobFail(
                    submitId = submitId,
                    submitDataId = submitDataId,
                )
                throw Exception()
            }
        } ?: run {
            log.trace("submit data가 없어, sleep 진행.")
            Thread.sleep(SLEEP_TIME)
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun generativeModelJobSuccess(
        submitId: Long,
        submitDataId: Long,
        serviceType: ServiceType,
        response: ChatCompletionsMessageResponse,
    ) {

        // 결과 저장
        val generativeService = generativeFactory.createGenerativeProvider(serviceType)
        generativeService.saveGenerativeResult(
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

        throw Exception()

        // 성공으로 업데이트
        submitDataService.updateStatus(
            submitDataId = submitDataId,
            changeStatus = SubmitDataStatus.SUCCESS,
        )

        // 아이템 개수 체크 후 Completed로 변경 해야함
        submitService.verifyAllSubmittedDataSuccess(submitId = submitId)
    }

    @Transactional
    fun generativeModelJobFail(
        submitId: Long,
        submitDataId: Long,
    ) {
        submitDataService.updateStatus(
            submitDataId = submitDataId,
            changeStatus = SubmitDataStatus.RESEND,
        )

        // 횟수 1 플러스하고, 체크
        submitService.increaseAttemptsAndCheckFailure(submitId = submitId)
    }


    companion object {
        const val SLEEP_TIME: Long = 1000 * 10 // 1000ms * 10 = 10s
    }
}