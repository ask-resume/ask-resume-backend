package app.askresume.api.job.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "직업 리스트 Response")
data class JobResponse(
    @field:Schema(description = "job id", example = "1")
    val id: Long,
    @field:Schema(description = "직업 영어명", example = "backend developer")
    val name: String,
)
