package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExpectedQuestionsResponse(
        @Schema(description = "질문 타입", example = "CS", required = true)
        String type,

        @Schema(description = "예상 질문", example = "회사에 지원한 이유가 어떻게 되나요?", required = true)
        String question
) {
}
