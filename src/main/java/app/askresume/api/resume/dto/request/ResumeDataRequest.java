package app.askresume.api.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record ResumeDataRequest(
        @NotBlank
        @Schema(description = "이력서 타입", example = "introduction", required = true)
        String resumeType,

        @NotBlank
        @Schema(description = "내용", required = true)
        String content
) {
}
