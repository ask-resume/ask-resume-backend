package app.askresume.api.submit.vo

import app.askresume.domain.submit.constant.Satisfaction
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime
import java.time.ZonedDateTime

data class SubmitResponse(
    @field:Schema(description = "제출 ID", example = "1")
    val submitId: Long,

    @field:Schema(description = "제출 제목", example = "안녕하세요. 동료와 함께 성장하는 개발자 성이름입니다.")
    val title: String,

    @field:Schema(description = "타겟 서비스", example = "INTERVIEW_MAKER")
    val serviceType: ServiceType,

    @field:Schema(description = "제출 상태", example = "WAITING")
    val submitStatus: SubmitStatus,

    @field:Schema(description = "생성일", example = "2023-06-17T15:45:20Z")
    val createdAt: ZonedDateTime,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubmitDetailResponse(
    @field:Schema(description = "타겟 서비스", example = "INTERVIEW_MAKER")
    val serviceType: ServiceType,
    @field:Schema(description = "serviceType = INTERVIEW_MAKER")
    val interviewMakerList: List<InterviewMakerVo> = listOf(),
)

data class InterviewMakerVo(
    @field:Schema(description = "예상 질문", example = "어쩌고...저쩌고...")
    val question: String,
    @field:Schema(description = "모범 답안", example = "어쩌고...저쩌고...")
    val bestAnswer: String,
    @field:Schema(description = "만족도", example = "NO_RESPONSE")
    val satisfaction : Satisfaction,
)