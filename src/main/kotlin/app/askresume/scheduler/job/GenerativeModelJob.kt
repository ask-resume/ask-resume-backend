package app.askresume.scheduler.job

import app.askresume.domain.prompt.service.PromptService
import app.askresume.domain.submit.service.SubmitDataService
import app.askresume.domain.submit.service.SubmitQueryService
import app.askresume.domain.submit.service.SubmitService
import app.askresume.external.openai.service.OpenAiService
import org.springframework.stereotype.Service

@Service
class GenerativeModelJob(
    private val submitService: SubmitService,
    private val submitDataService: SubmitDataService,
    private val submitQueryService: SubmitQueryService,
    private val openAiService: OpenAiService,
    private val promptService: PromptService,
) {

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
            openAiService.requestOpenAi(createdChatCompletionsRequest)


            // 결과 저장

            // result 데이터 저장 (토큰량)
        }
    }

}