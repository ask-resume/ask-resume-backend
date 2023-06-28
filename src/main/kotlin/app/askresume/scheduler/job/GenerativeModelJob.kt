package app.askresume.scheduler.job

import app.askresume.domain.result.service.ResultService
import app.askresume.domain.submit.service.SubmitQueryService
import app.askresume.external.openai.service.OpenAiService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenerativeModelJob(
    private val submitQueryService: SubmitQueryService,
    private val openAiService: OpenAiService,
    private val resultService: ResultService,
) {

    @Transactional
    fun execute() {
        val submitQueryData = submitQueryService.findRequestedFirstSubmit()

        if (submitQueryData != null) {

            // 프롬프트를 찾고, 데이터 프롬프트에 맞춰 데이터 끼워 넣음
            // 여기서 open ai dto로 받음 openAiService.
            val createdChatCompletionsRequest = openAiService.createdChatCompletionsRequest(
                serviceType = submitQueryData.serviceType,
                parameter = submitQueryData.parameter,
            )

            // 데이터로 요청
            val response = openAiService.requestOpenAiChatCompletion(createdChatCompletionsRequest)

            // 결과 저장


            // Result 데이터 저장 (토큰량)
            resultService.saveResultResponseData(
                model = response.model,
                created = response.created,
                promptTokens = response.usage.promptTokens,
                contentToken = response.usage.completionTokens,
                totalTokens = response.usage.totalTokens
            )
        }
    }

}