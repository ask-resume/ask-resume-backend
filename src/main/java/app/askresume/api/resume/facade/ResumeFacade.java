package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.api.resume.dto.response.PdfToTextResponse;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.domain.gpt.service.GptService;
import app.askresume.domain.resume.service.ResumeService;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

    public PdfToTextResponse toText(MultipartFile file) {
        String text = "";

        try (InputStream inputStream = file.getInputStream()) {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            text = pdfTextStripper.getText(document);
            document.close();
        } catch (IOException e) {
            log.error("PDF파일을 TEXT로 저장하는 도중 ERROR 발생 \n에러 내용 : {}", e.getMessage());
            throw new BusinessException(ErrorCode.IO_ERROR);
        }

        log.debug(text);

        return PdfToTextResponse.builder()
                .text(text)
                .build();
    }
}
