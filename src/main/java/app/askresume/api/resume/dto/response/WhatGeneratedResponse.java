package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record WhatGeneratedResponse(
        @Schema(description = "예상 질문 리스트", required = true)
        List<ExpectedQuestionsResponse> expectedQuestions

//        @Schema(description = "강점 리스트", example = "다양한경험, 호기심")
//        List<String> merit,
//
//        @Schema(description = "단점 리스트", example = "짧은경력, 잦은 이직")
//        List<String> disadvantages

) {
    public WhatGeneratedResponse() {
//        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this(new ArrayList<>());
    }
}
