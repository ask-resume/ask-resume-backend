package app.askresume.oauth

import app.askresume.oauth.constant.OAuthProvider
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth")
data class OAuthProperties(
    val domain: String,
    val clientHome: String,
    val providers: Map<OAuthProvider, OAuthProviderProperties>
)

data class OAuthProviderProperties(
    val clientId: String,
    val clientSecret: String,
    val authorizationUrl: String,
    val userInfoUrl: String,
    val tokenUrl: String,
    val redirectUrl: String,
    val emailUrl: String?,
    val scope: List<String>?,
)