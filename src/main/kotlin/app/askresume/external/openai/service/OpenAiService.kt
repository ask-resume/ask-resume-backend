package app.askresume.external.openai.service

import app.askresume.domain.prompt.service.PromptService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.external.openai.client.OpenAiClient
import app.askresume.external.openai.constant.OpenAiRole
import app.askresume.external.openai.dto.ChatCompletionsMessageDto
import app.askresume.external.openai.dto.ChatCompletionsMessageResponse
import app.askresume.external.openai.dto.ChatCompletionsRequest
import app.askresume.global.jwt.constant.GrantType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OpenAiService(
    private val openAiClient: OpenAiClient,
    private val promptService: PromptService,
) {


    @Value("\${external.openai.token}")
    lateinit var token: String

    fun createdChatCompletionsRequest(
        serviceType: ServiceType,
        parameter: Map<String, Any>
    ): ChatCompletionsRequest {

        val prompt = promptService.findPromptAndFormatting(
            serviceType = serviceType,
            parameter = parameter
        )

        return ChatCompletionsRequest(
            messages = arrayListOf(
                ChatCompletionsMessageDto(
                    role = OpenAiRole.SYSTEM.value,
                    content = prompt
                ),
                ChatCompletionsMessageDto(
                    role = OpenAiRole.USER.value,
                    content = parameter["content"] as String
                ),
            )
        )
    }

    fun requestOpenAiChatCompletion(
        request: ChatCompletionsRequest
    ): ChatCompletionsMessageResponse {
        return openAiClient.createChatCompletion(
            accessToken = "${GrantType.BEARER.type} $token",
            request = request
        )
    }
}