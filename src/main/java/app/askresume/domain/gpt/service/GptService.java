package app.askresume.domain.gpt.service;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.domain.gpt.template.Prompt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.List;

import static app.askresume.domain.gpt.config.GptConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    @Value("${openai.token}")
    private String OPENAI_TOKEN;

    private final ObjectMapper objectMapper;

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

    public List<ChatMessage> generateMessage(GenerateQuestionRequest request) {
        final String job = request.getJob();
        final String difficulty = request.getDifficulty();
        final String prompt = Prompt.generatePrompt(job, difficulty);

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), request.getContent());

        return List.of(systemMessage, userMessage);
    }
}