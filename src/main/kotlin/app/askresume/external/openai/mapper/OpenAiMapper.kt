package app.askresume.external.openai.mapper

import app.askresume.external.openai.constant.OpenAiRole
import app.askresume.external.openai.dto.ChatCompletionsMessageDto
import app.askresume.external.openai.dto.ChatCompletionsRequest
import app.askresume.global.annotation.Mapper

@Mapper
class OpenAiMapper {

    fun promptAndContentToChatCompletionsRequest(
        prompt: String,
        content: String,
    ): ChatCompletionsRequest {

        return ChatCompletionsRequest(
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