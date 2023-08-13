package app.askresume.api.job.vo

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length

@Schema(description = "어드민 직업정보 등록 Request")
data class SaveJobRequest(
    @field:Length(max = 150)
    @field:Schema(description = "직업 영어명", example = "backend developer")
    val englishJobName: String,

    @field:Length(max = 150)
    @field:Schema(description = "직업 한글명", example = "백엔드 개발자")
    val koreaJobName: String,
)
