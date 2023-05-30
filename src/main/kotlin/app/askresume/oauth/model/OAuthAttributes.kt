package app.askresume.oauth.model

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member

data class OAuthAttributes(
    val email: String,
    val name: String,
    val profile: String,
    val locale: String,
    val memberType: MemberType,
) {
    fun toMemberEntity(memberType: MemberType, role: Role): Member {
        return Member(
            username = name,
            email = email,
            memberType = memberType,
            profile = profile,
            locale = locale,
            role = role,
        )
    }
}

