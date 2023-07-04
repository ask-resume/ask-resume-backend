package app.askresume.oauth.userinfo

import app.askresume.domain.member.constant.MemberType
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.exception.CannotReadOAuthUserInfoException

class LinkedInUserInfo(
    attributes: Map<String, Any>,
) : OAuthUserInfo(attributes) {

    override val email: String
        get() {
            val elements = attributes["elements"] as? List<*>
            val handle = elements?.get(0) as? Map<*, *>
            val handle2 = handle?.get("handle~") as? Map<*, *>
            return handle2?.get("emailAddress") as? String
                ?: throw CannotReadOAuthUserInfoException(OAuthProvider.LINKED_IN.name)
        }
    override val name: String
        get() {
            val firstName = attributes["localizedFirstName"]
            val lastName = attributes["localizedLastName"]
            return "$firstName $lastName"
        }
    override val profile: String?
        get() {
            val profilePicture = attributes["profilePicture"] as? Map<*, *>
            return profilePicture?.get("displayImage") as? String?
        }
    override val locale: String
        get() {
            val firstName = attributes["firstName"] as? Map<*, *>
            val preferredLocale = firstName?.get("preferredLocale") as? Map<*, *>
            return preferredLocale?.get("language") as? String
                ?: throw CannotReadOAuthUserInfoException(OAuthProvider.LINKED_IN.name)
        }
    override val memberType get() = MemberType.LINKED_IN

}