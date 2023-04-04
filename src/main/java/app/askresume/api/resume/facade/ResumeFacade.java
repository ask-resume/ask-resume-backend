package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.api.resume.dto.response.FutureWhatGeneratedResponse;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.mapper.CareerYearMapper;
import app.askresume.api.resume.mapper.GenerateExpectedQuestionMapper;
import app.askresume.domain.gpt.service.GptService;
import app.askresume.domain.job.service.JobService;
import app.askresume.domain.resume.service.ResumeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeFacade {

    private final ResumeService resumeService;
    private final GptService gptService;

    private final JobService jobService;
    private final ObjectMapper mapper;
    private final GenerateExpectedQuestionMapper generateExpectedQuestionMapper;
    private final CareerYearMapper careerYearMapper;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public WhatGeneratedResponse generate(final GenerateExpectedQuestionRequest request) {

        final String job = jobService.findJobNameById(request.jobId()).getMasterName();
        final String difficulty = request.difficulty();
        final String careerYear = careerYearMapper.toCareer(request.careerYear());
        final List<ResumeDataRequest> resumeData = generateExpectedQuestionMapper.toResumeData(request.contents());

        // 멀티스레드 호출
        List<CompletableFuture<FutureWhatGeneratedResponse>> futures = new ArrayList<>();

        for (ResumeDataRequest data : resumeData) {
            CompletableFuture<FutureWhatGeneratedResponse> future = CompletableFuture.supplyAsync(() -> {
                List<ChatMessage> chatMessages = gptService.generateMessage(job, difficulty, careerYear, data.resumeType(), data.content());
                try {
                    log.debug("호출되는가?");
                    ChatCompletionResult chatCompletionResult = gptService.generate(chatMessages);
                    log.debug("사용한 토큰 : {}", chatCompletionResult.getUsage().getTotalTokens());
                    String futureResult = chatCompletionResult.getChoices().get(0).getMessage().getContent();
                    return mapper.readValue(futureResult, FutureWhatGeneratedResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }, executorService);
            futures.add(future);
        }

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allFutures.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // CompletableFuture에서 결과를 추출해서 WhatGeneratedResponse 객체에 저장
        WhatGeneratedResponse result = new WhatGeneratedResponse();
        futures.forEach(future -> {
            try {
                FutureWhatGeneratedResponse content = future.get();
                if (content.expectedQuestions().size() != 0) {
                    content.expectedQuestions().forEach(array -> result.expectedQuestions().add(array));
                }
//                result.merit().add(content.merit());
//                result.disadvantages().add(content.disadvantages());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        // request 내용, 생성된 질문 저장

        // 리스폰
        return result;
    }

}