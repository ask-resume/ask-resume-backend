package app.askresume.domain.gpt.service;

import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.domain.gpt.template.Prompt;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static app.askresume.domain.gpt.config.GptConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    @Value("${openai.token}")
    private String OPENAI_TOKEN;

    private final ObjectMapper objectMapper;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ChatCompletionResult generate(List<ChatMessage> chatMessages) {
        OpenAiService openAiService = new OpenAiService(OPENAI_TOKEN, TIME_OUT);

        ChatCompletionRequest build = ChatCompletionRequest.builder()
                .messages(chatMessages)
                .maxTokens(MAX_TOKEN)
                .temperature(TEMPERATURE)
                .topP(TOP_P)
                .model(MODEL)
                .build();

        // java.net.SocketTimeoutException 구현

        return openAiService.createChatCompletion(build);

    }
    public List<ChatMessage> generateMessage(String job, String difficulty, String careerYear, String resumeType, String content) {
        final String prompt = Prompt.generatePrompt(job, difficulty, careerYear, resumeType);

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);

        return List.of(systemMessage, userMessage);
    }

    public WhatGeneratedResponse createdExpectedQuestionsAndAnswer(String job, String difficulty, String careerYear, List<ResumeDataRequest> resumeData) {

        // 멀티스레드 호출
        List<CompletableFuture<WhatGeneratedResponse>> futures = new ArrayList<>();

        for (ResumeDataRequest data : resumeData) {
            CompletableFuture<WhatGeneratedResponse> future = CompletableFuture.supplyAsync(() -> {
                List<ChatMessage> chatMessages =  generateMessage(job, difficulty, careerYear, data.resumeType(), data.content());
                try {
                    log.debug("호출되는가?");
                    ChatCompletionResult chatCompletionResult = generate(chatMessages);

                    log.debug("사용한 토큰 : {}", chatCompletionResult.getUsage().getTotalTokens());
                    String futureResult = chatCompletionResult.getChoices().get(0).getMessage().getContent();

                    log.debug(futureResult);

                    return objectMapper.readValue(futureResult, WhatGeneratedResponse.class);
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                    throw new BusinessException(ErrorCode.JSON_PARSING_FAILED);
                }
            }, executorService);
            futures.add(future);
        }

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allFutures.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.THREAD_MALFUNCTION);
        }

        // CompletableFuture에서 결과를 추출해서 WhatGeneratedResponse 객체에 저장
        WhatGeneratedResponse result = new WhatGeneratedResponse();
        futures.stream()
                .map(CompletableFuture::join)
                .filter(content -> content.predictionResponse().size() != 0)
                .forEach(content -> result.predictionResponse().addAll(content.predictionResponse()));

        return result;
    }
}