package app.askresume.oauth.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

class OAuthResponse {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Token(
        val accessToken: String,
        val expiresIn: Long,
        val refreshToken: String?,
        val refreshTokenExpiresIn: Long?,
        val scope: String,
    )

}