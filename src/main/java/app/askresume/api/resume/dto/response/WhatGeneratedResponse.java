package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class WhatGeneratedResponse {

    @Schema(description = "예상 질문 리스트", required = true)
    private List<ExpectedQuestions> expectedQuestions;

    @Schema(description = "강점 리스트", example = "다양한경험, 호기심")
    private List<String> strengths;

    @Schema(description = "단점 리스트", example = "짧은경력, 잦은 이직")
    private List<String> disadvantages;

    @Getter
    @Setter
    public static class ExpectedQuestions {

        @Schema(description = "질문 타입", example = "CS", required = true)
        private String type;

        @Schema(description = "예상 질문", example = "회사에 지원한 이유가 어떻게 되나요?", required = true)
        private String question;
    }

}
