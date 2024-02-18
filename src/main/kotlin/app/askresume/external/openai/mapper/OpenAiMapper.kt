package app.askresume.external.openai.mapper

import app.askresume.domain.submit.constant.ServiceType
import app.askresume.external.openai.constant.OpenAiRole
import app.askresume.external.openai.dto.ChatCompletionsMessageDto
import app.askresume.external.openai.dto.ChatCompletionsRequest

object OpenAiMapper {

    fun promptAndContentToChatCompletionsRequest(
        serviceType : ServiceType,
        prompt: String,
        content: String,
    ): ChatCompletionsRequest {

        return ChatCompletionsRequest(
            model = serviceType.model,
            maxTokens = serviceType.maxTokens,
            messages = arrayListOf(
                ChatCompletionsMessageDto(
                    role = OpenAiRole.SYSTEM.value,
                    content = prompt
                ),
                ChatCompletionsMessageDto(
                    role = OpenAiRole.USER.value,
                    content = content
                ),
            )
        )
    }

}