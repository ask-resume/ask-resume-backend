package app.askresume.api.resume.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class GenerateExpectedQuestionRequest(
    @field:Schema(
        description = "직업 ID",
        example = "120",
        required = true
    )
    val jobId: @NotNull Long,

    @field:Schema(
        description = "난이도",
        example = "medium",
        required = true
    )
    @NotBlank
    val difficulty: String,

    @field:Schema(
        description = "경력",
        example = "2",
        required = true
    )
    @Min(value = 0)
    @Max(value = 30)
    val careerYear: Int,

    @field:Schema(
        description = "언어",
        example = "ko",
        required = true
    )
    @NotBlank
    val locale: String,

    @field:Schema(
        description = "이력서 상세 내용",
        required = true
    )
    @Valid
    val contents: MyResume
) {

    data class MyResume(

        @Schema(description = "자기소개서")
        @Valid
        val introduction: MutableList<Content>?,

        @Schema(description = "경력이력")
        @Valid
        val career: MutableList<Content>?,

        @Schema(description = "기술스택")
        @Valid
        val technical: MutableList<Content>?,

        @Schema(description = "프로젝트 경험")
        @Valid
        val project: MutableList<Content>?,

        @Schema(description = "교육이력")
        @Valid
        val education: MutableList<Content>?,

        @Schema(description = "대외 활동")
        @Valid
        val outsideActivities: MutableList<Content>?,

        @Schema(description = "자격증,어학,수상내역(achievements_and_credentials)")
        @Valid
        val aac: MutableList<Content>?,

    ) {
        class Content(
            @Length(min = 100, max = 1000, message = "컨텐츠는 길이는 100자에서 1,000자 사이어야합니다.")
            val content: String
        )
    }

    fun updateLocale(newLocale: String): GenerateExpectedQuestionRequest {
        return GenerateExpectedQuestionRequest(
            jobId, difficulty, careerYear, newLocale, contents
        )
    }
}