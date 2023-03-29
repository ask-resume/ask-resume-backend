package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.domain.gpt.service.GptService;
import app.askresume.domain.resume.service.ResumeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeFacade {

    private final ResumeService resumeService;
    private final GptService gptService;
    private final ObjectMapper mapper;

    public WhatGeneratedResponse generate(GenerateQuestionRequest request) throws JsonProcessingException {

        // 토큰 개수 체크로직

        // 예상 질문 생성
        List<ChatMessage> chatMessages = gptService.generateMessage(request);
        ChatCompletionResult result = gptService.generate(chatMessages);

        // request 내용, 생성된 질문 저장

        // 리스폰

        log.debug("토큰 사용량 : {}", String.valueOf(result.getUsage().getTotalTokens()));

        final String content = result.getChoices().get(0).getMessage().getContent();

        return mapper.readValue(content, WhatGeneratedResponse.class);
    }

}