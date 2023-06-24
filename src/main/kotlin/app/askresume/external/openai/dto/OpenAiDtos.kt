package app.askresume.external.openai.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsRequest(
    val model: String = "gpt-3.5-turbo",
    val maxTokens: Int = 2000,
    val messages: MutableList<ChatCompletionsMessageDto> = mutableListOf(),
) {
    override fun toString(): String {
        return "ChatCompletionsRequest(model='$model', maxTokens=$maxTokens, messages=$messages)"
    }
}

data class ChatCompletionsMessageDto(
    val role: String,
    val content: String,
) {
    override fun toString(): String {
        return "ChatCompletionsMessageDto(role='$role', content='$content')"
    }
}