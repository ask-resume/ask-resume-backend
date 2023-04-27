package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PredictionResponse(
        @Schema(description = "질문 타입", example = "Behavioral", required = true)
        String type,

        @Schema(description = "예상 질문", example = "Can you tell me about a time when you had difficul....", required = true)
        String question,

        @Schema(description = "모범 답안", example = "I once had to work on a project that involved int....", required = true)
        String bestAnswer
) {
}
