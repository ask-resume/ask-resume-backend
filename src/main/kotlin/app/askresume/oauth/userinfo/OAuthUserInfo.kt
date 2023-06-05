package app.askresume.oauth.userinfo

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member

abstract class OAuthUserInfo(
    protected val attributes: Map<String, Any>,
) {

    abstract val email: String
    abstract val name: String
    abstract val profile: String?
    abstract val locale: String
    abstract val memberType: MemberType

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