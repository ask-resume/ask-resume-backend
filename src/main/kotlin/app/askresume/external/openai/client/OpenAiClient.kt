package app.askresume.external.openai.client

import app.askresume.external.openai.dto.ChatCompletionsRequest
import app.askresume.global.config.FeignConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "open-ai", url = "\${openai.url}", configuration = [FeignConfiguration::class])
interface OpenAiClient {

    // gpt-3.5-turbo
    @PostMapping("/v1/chat/completions", consumes = ["application/json"])
    fun createChatCompletion(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String,
        @RequestBody request: ChatCompletionsRequest
    )

}