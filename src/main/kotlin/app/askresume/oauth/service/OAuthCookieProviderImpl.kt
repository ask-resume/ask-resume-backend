package app.askresume.oauth.service

import app.askresume.oauth.dto.OAuthResponse
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service

@Service
class OAuthCookieProviderImpl : OAuthCookieProvider {

    override fun generateAccessTokenCookie(oAuthTokenDTO: OAuthResponse.Token): ResponseCookie {
        return ResponseCookie.from("access_token", oAuthTokenDTO.accessToken)
        .httpOnly(true)
        .domain("localhost")
        .path("/")
        .maxAge(oAuthTokenDTO.expiresIn)
        .build()
    }

    override fun generateRefreshTokenCookie(oAuthTokenDTO: OAuthResponse.Token): ResponseCookie {
        return ResponseCookie.from("refresh_token", oAuthTokenDTO.refreshToken ?: "")
            .httpOnly(true)
            .domain("localhost")
            .path("/")
            .maxAge(oAuthTokenDTO.expiresIn)
            .build()
    }
}