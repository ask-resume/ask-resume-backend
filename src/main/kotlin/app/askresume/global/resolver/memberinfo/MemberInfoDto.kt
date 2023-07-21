package app.askresume.global.resolver.memberinfo

import app.askresume.domain.member.constant.Role
import io.swagger.v3.oas.annotations.Hidden

@Hidden
data class MemberInfoDto(
    val memberId: Long,
    val role: Role,
)
