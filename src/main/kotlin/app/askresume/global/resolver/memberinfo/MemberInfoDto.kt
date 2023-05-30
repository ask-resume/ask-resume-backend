package app.askresume.global.resolver.memberinfo

import app.askresume.domain.member.constant.Role

data class MemberInfoDto(
    val memberId: Long,
    val role: Role,
)
