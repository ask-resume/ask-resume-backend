package app.askresume.oauth.dto

import app.askresume.oauth.OAuthProviderProperties
import app.askresume.oauth.constant.OAuthGrantType

class OAuthRequest {

    data class Token(
        val grantType: String,
        val code: String,
        val clientId: String,
        val clientSecret: String,
        val redirectUrl: String,
    ) {

        constructor(
            grantType: OAuthGrantType,
            code: String,
            providerProperties: OAuthProviderProperties
        ) : this(
            grantType = grantType.value,
            code = code,
            clientId = providerProperties.clientId,
            clientSecret = providerProperties.clientSecret,
            redirectUrl = providerProperties.redirectUrl,
        )

    }

}