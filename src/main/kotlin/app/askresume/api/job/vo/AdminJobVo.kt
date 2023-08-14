package app.askresume.api.job.vo

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

@Schema(name = "어드민 직업정보 등록 Request")
data class SaveJobRequest(
    @field:NotBlank
    @field:Length(max = 150)
    @field:Schema(description = "직업 영어명", example = "backend developer", required = true)
    val englishJobName: String,

    @field:NotBlank
    @field:Length(max = 150)
    @field:Schema(description = "직업 한글명", example = "백엔드 개발자", required = true)
    val koreaJobName: String,
)
