package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExpectedQuestionsResponse(
        @Schema(description = "질문 타입", example = "Behavioral", required = true)
        String type,

        @Schema(description = "예상 질문", example = "Can you tell me about a time when you had difficulty understanding someone else's code? How did you handle the situation?", required = true)
        String question
) {
}
