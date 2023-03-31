package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.domain.gpt.service.GptService;
import app.askresume.domain.resume.service.ResumeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeFacade {

    private final ResumeService resumeService;
    private final GptService gptService;
    private final ObjectMapper mapper;

    public WhatGeneratedResponse generate(GenerateExpectedQuestionRequest request) throws JsonProcessingException, IllegalAccessException {

        // 토큰 개수 체크로직

        // 예상 질문 생성

        final String job = request.job();
        final String difficulty = request.difficulty();
        final int careerYear = request.careerYear();

        request.contents();

        // Reflection API
        Field[] fields = request.contents().getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(List.class)) {
                List<GenerateExpectedQuestionRequest.MyResume.Content> contentList = (List<GenerateExpectedQuestionRequest.MyResume.Content>) field.get(request.contents());
                if (contentList != null) {
                    System.out.println(field.getName() + " : ");
                    for (GenerateExpectedQuestionRequest.MyResume.Content content : contentList) {
                        if (content != null && content.content() != null) {
                            System.out.println(content.content());
                        }
                    }
                }
            }
        }

        //List<ChatMessage> chatMessages = gptService.generateMessage(request);
        //ChatCompletionResult result = gptService.generate(chatMessages);

        // request 내용, 생성된 질문 저장

        // 리스폰

        //final String content = result.getChoices().get(0).getMessage().getContent();

        //return mapper.readValue("content", WhatGeneratedResponse.class);
        return null;
    }

}