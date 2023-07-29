package app.askresume.domain.member.dto

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class MemberInfoDto @QueryProjection constructor(
    val memberId: Long,
    val email: String,
    val memberType: MemberType,
    val locale: String,
    val role: Role,
    val username: String,
    val profile: String?,
    val refreshToken: String?,
    val tokenExpirationTime: LocalDateTime?
)