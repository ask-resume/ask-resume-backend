package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.domain.gpt.service.GptService;
import app.askresume.domain.resume.service.ResumeService;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeFacade {

    private final ResumeService resumeService;
    private final GptService gptService;

    public void generate(GenerateQuestionRequest request) {

        // 토큰 개수 체크로직


        // 예상 질문 생성
        List<ChatMessage> chatMessages = gptService.generateMessage(request);
        ChatCompletionResult result = gptService.generate(chatMessages);


        // request 내용, 생성된 질문 저장

    }
}
