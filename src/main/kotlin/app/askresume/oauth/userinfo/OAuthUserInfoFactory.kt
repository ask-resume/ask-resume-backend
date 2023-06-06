package app.askresume.oauth.userinfo

import app.askresume.oauth.constant.OAuthProvider
import org.springframework.stereotype.Component

@Component
class OAuthUserInfoFactory {

    fun create(provider: OAuthProvider, oAuthAttributes: Map<String, Any>): OAuthUserInfo {
        return when (provider) {
            OAuthProvider.GOOGLE -> GoogleUserInfo(oAuthAttributes)
            OAuthProvider.LINKED_IN -> LinkedInUserInfo(oAuthAttributes)
        }
    }

}