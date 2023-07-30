package app.askresume.domain.submit.dto

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime


data class SubmitDto @QueryProjection constructor(
    val memberId : Long,
    val email : String,
    val memberType : MemberType,
    val submitId : Long,
    val title: String,
    val dataCount: Int,
    val attempts: Int,
    val submitStatus: SubmitStatus,
    val serviceType: ServiceType,
    val createdAt : ZonedDateTime,
    val updatedAt : ZonedDateTime?,
)


data class FirstSubmittedDto @QueryProjection constructor(
    val submitId: Long,
    val submitDataId: Long,
    val serviceType: ServiceType,
    val parameter: Map<String, Any> = mapOf(),
)
