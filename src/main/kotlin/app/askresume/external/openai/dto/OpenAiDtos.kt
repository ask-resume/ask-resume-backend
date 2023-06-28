package app.askresume.external.openai.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsRequest(
    val model: String = "gpt-3.5-turbo",
    val maxTokens: Int = 2000,
    val messages: MutableList<ChatCompletionsMessageDto> = mutableListOf(),
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
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
    //@JsonProperty("finish_reason")
    val finishReason: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UsageDto(
    //@JsonProperty("prompt_tokens")
    val promptTokens: Int,
    //@JsonProperty("completion_tokens")
    val completionTokens: Int,
    //@JsonProperty("total_tokens")
    val totalTokens: Int,
)