package app.askresume.oauth.userinfo

import app.askresume.domain.member.constant.MemberType

class GoogleUserInfo(
    attributes: Map<String, Any>,
) : OAuthUserInfo(attributes) {

    override val email get() = attributes["email"] as String
    override val name get() = attributes["name"] as String
    override val profile get() = attributes["picture"] as String?
    override val locale get() = attributes["locale"] as String
    override val memberType get() = MemberType.GOOGLE

}