package app.askresume.external.openai.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsRequest(
    val model: String,
    val maxTokens: Int,
    val messages: List<ChatCompletionsMessageDto> = mutableListOf(),
)

data class ChatCompletionsMessageDto(
    val role: String,
    val content: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsMessageResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<ChoicesDto> = listOf(),
    val usage: UsageDto,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChoicesDto(
    val index: Int,
    val message: ChatCompletionsMessageDto,
    val finishReason: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UsageDto(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
)