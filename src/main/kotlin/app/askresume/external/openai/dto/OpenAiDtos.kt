package app.askresume.external.openai.dto

data class ChatCompletionsRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: MutableList<ChatCompletionsMessageDto> = mutableListOf(),
) {}

data class ChatCompletionsMessageDto(
    val role: String,
    val content: String,
) {}