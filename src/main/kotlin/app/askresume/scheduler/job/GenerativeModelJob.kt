package app.askresume.scheduler.job

import app.askresume.domain.generative.factory.GenerativeFactory
import app.askresume.domain.result.service.ResultService
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.service.SubmitDataService
import app.askresume.domain.submit.service.SubmitQueryService
import app.askresume.external.openai.service.OpenAiService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenerativeModelJob(
    private val submitQueryService: SubmitQueryService,
    private val submitDataService: SubmitDataService,
    private val openAiService: OpenAiService,
    private val resultService: ResultService,
    private val generativeFactory: GenerativeFactory,
) {

    @Transactional
    fun execute() {
        val submitQueryData = submitQueryService.findRequestedFirstSubmit()

        if (submitQueryData != null) {

            val (submitId, submitDataId, serviceType, parameter) = submitQueryData

            val createdChatCompletionsRequest = openAiService.createdChatCompletionsRequest(
                serviceType = serviceType,
                parameter = parameter,
            )

            // 데이터로 요청
            val response = openAiService.requestOpenAiChatCompletion(createdChatCompletionsRequest)

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

            submitDataService.updateStatus(
                submitDataId = submitDataId,
                changeStatus = SubmitDataStatus.SUCCESS,
            )
        }
    }

}