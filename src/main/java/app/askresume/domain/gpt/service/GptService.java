package app.askresume.domain.gpt.service;

import app.askresume.domain.gpt.template.Prompt;
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
    public List<ChatMessage> generateMessage(String job, String difficulty, String careerYear, String resumeType, String content) {
        final String prompt = Prompt.generatePrompt(job, difficulty, careerYear, resumeType);

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);

        return List.of(systemMessage, userMessage);
    }
}