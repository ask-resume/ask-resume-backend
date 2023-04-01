package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FutureWhatGeneratedResponse(
        @Schema(description = "예상 질문 리스트", required = true)
        List<ExpectedQuestionsResponse> expectedQuestions,

        @Schema(description = "장점", example = "다양한경험")
        String merit,

        @Schema(description = "단점", example = "짧은경력")
        String disadvantages

) {
}
