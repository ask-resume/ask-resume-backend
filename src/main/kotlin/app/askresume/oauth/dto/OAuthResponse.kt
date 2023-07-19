package app.askresume.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

class OAuthResponse {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Token(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("expires_in")
        val expiresIn: Long,
        @JsonProperty("refresh_token")
        val refreshToken: String?,
        @JsonProperty("refresh_token_expires_in")
        val refreshTokenExpiresIn: Long?,
        @JsonProperty("scope")
        val scope: String,
    )

}