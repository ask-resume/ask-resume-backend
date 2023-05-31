package app.askresume.api.resume.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

data class ResumeDataRequest(
    @field:Schema(description = "이력서 타입", example = "introduction", required = true)
    @field:NotBlank
    val resumeType: String,

    @field:Schema(description = "내용", required = true)
    @field:NotBlank
    val content: String?,
)

