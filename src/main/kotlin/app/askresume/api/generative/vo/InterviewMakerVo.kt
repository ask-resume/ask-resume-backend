package app.askresume.api.generative.vo

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Schema(name = "모의인터뷰 생성 제출 Request")
data class InterviewMakerRequest(
    @field:Schema(description = "직업 ID", example = "120", required = true)
    @field:NotNull
    val jobId: Long,

    @field:Schema(description = "난이도", example = "medium", required = true)
    @field:NotBlank
    val difficulty: String,

    @field:Schema(description = "경력", example = "2", required = true)
    @field:Min(value = 0)
    @field:Max(value = 30)
    val careerYear: Int,

    @field:Schema(description = "언어", example = "ko", required = true)
    @field:NotBlank
    val language: String,

    @field:Schema(description = "이력서 상세 내용", required = true)
    @field:Valid
    val contents: ResumeInformationVo
)


@Schema(name = "이력서 상세정보")
data class ResumeInformationVo(

    @field:Schema(description = "자기소개서")
    @field:Valid
    val introduction: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "경력이력")
    @field:Valid
    val career: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "기술스택")
    @field:Valid
    val technical: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "프로젝트 경험")
    @field:Valid
    val project: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "교육이력")
    @field:Valid
    val education: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "대외 활동")
    @field:Valid
    val outsideActivities: MutableList<Content> = mutableListOf(),

    @field:Schema(description = "자격증,어학,수상내역(achievements_and_credentials)")
    @field:Valid
    val aac: MutableList<Content> = mutableListOf(),
)

@Schema(name = "내용")
data class Content(
    @field:Schema(description = "내용")
    @field:Length(min = 100, max = 1000, message = "컨텐츠는 길이는 100자에서 1,000자 사이어야합니다.")
    val content: String
)

@Schema(name = "이력서 내용")
data class ResumeDataVo(
    @field:Schema(description = "이력서 타입", example = "introduction", required = true)
    @field:NotBlank
    val resumeType: String,

    @field:Schema(description = "내용", required = true)
    @field:NotBlank
    val content: String,
)
