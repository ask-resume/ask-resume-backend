package app.askresume.external.gpt.service

import app.askresume.api.resume.dto.request.ResumeDataRequest
import app.askresume.api.resume.dto.response.WhatGeneratedResponse
import app.askresume.external.gpt.config.GptConfig
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import app.askresume.global.util.LoggerUtil.log
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors


@Service
class GptService(
    private val objectMapper: ObjectMapper,
) {

    val log = log()

    @Value("\${openai.token}")
    private lateinit var OPENAI_TOKEN: String

    private val executorService = Executors.newFixedThreadPool(1)

    fun generate(chatMessages: List<ChatMessage>?): ChatCompletionResult {
        val openAiService = OpenAiService(OPENAI_TOKEN, GptConfig.TIME_OUT)
        val build = ChatCompletionRequest.builder()
            .messages(chatMessages)
            .maxTokens(GptConfig.MAX_TOKEN)
            .temperature(GptConfig.TEMPERATURE)
            .topP(GptConfig.TOP_P)
            .model(GptConfig.MODEL)
            .build()
        return openAiService.createChatCompletion(build)
    }

    fun generateMessage(
        prompt: String,
        job: String,
        difficulty: String,
        careerYear: String,
        resumeType: String,
        locale: String,
        content: String?
    ): List<ChatMessage> {
        val bindingPrompt = String.format(prompt, job, resumeType, difficulty, careerYear, locale)

        log.debug("생성된 프롬프트 : {} ", bindingPrompt)

        val systemMessage = ChatMessage(ChatMessageRole.SYSTEM.value(), bindingPrompt)
        val userMessage = ChatMessage(ChatMessageRole.USER.value(), content)

        return listOf(systemMessage, userMessage)
    }

    fun createdExpectedQuestionsAndAnswer(
        prompt: String,
        job: String,
        difficulty: String,
        careerYear: String,
        locale: String,
        resumeData: List<ResumeDataRequest>
    ): WhatGeneratedResponse {

        // 멀티스레드 호출
        val futures: MutableList<CompletableFuture<WhatGeneratedResponse>> = ArrayList()

        for (data in resumeData) {
            val future = CompletableFuture.supplyAsync(
                {
                    val chatMessages =
                        generateMessage(prompt, job, difficulty, careerYear, data.resumeType, locale, data.content)
                    try {
                        log.debug("호출되는가?")
                        val chatCompletionResult = generate(chatMessages)

                        log.debug("사용한 토큰 : {}", chatCompletionResult.usage.totalTokens)
                        val futureResult =
                            chatCompletionResult.choices[0].message.content

                        log.debug(futureResult)

                        return@supplyAsync objectMapper.readValue<WhatGeneratedResponse>(
                            futureResult,
                            WhatGeneratedResponse::class.java
                        )
                    } catch (e: JsonProcessingException) {
                        log.error(e.message)
                        throw BusinessException(ErrorCode.JSON_PARSING_FAILED)
                    }
                }, executorService)

            futures.add(future)
        }

        // 모든 CompletableFuture가 완료될 때까지 대기
        val allFutures = CompletableFuture.allOf(*futures.toTypedArray<CompletableFuture<*>>())
        try {
            allFutures.get()
        } catch (e: InterruptedException) {
            log.error(e.message)
            throw BusinessException(ErrorCode.THREAD_MALFUNCTION)
        } catch (e: ExecutionException) {
            log.error(e.message)
            throw BusinessException(ErrorCode.THREAD_MALFUNCTION)
        }

        // CompletableFuture에서 결과를 추출해서 WhatGeneratedResponse 객체에 저장
        val result = WhatGeneratedResponse()
        futures.stream()
            .map { obj: CompletableFuture<WhatGeneratedResponse> -> obj.join() }
            .filter { content: WhatGeneratedResponse -> content.predictionResponse.size !== 0 }
            .forEach { content: WhatGeneratedResponse -> result.predictionResponse.addAll(content.predictionResponse) }

        return result
    }
}