package app.askresume.external.openai.service

import app.askresume.external.openai.client.OpenAiClient
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
) {

    @Value("\${external.openai.token}")
    private lateinit var token: String

    fun requestOpenAiChatCompletion(
        request: ChatCompletionsRequest
    ): ChatCompletionsMessageResponse {
        return openAiClient.createChatCompletion(
            accessToken = "${GrantType.BEARER.type} $token",
            request = request
        )
    }
}