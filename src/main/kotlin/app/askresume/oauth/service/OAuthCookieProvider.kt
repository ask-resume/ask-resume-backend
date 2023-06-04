package app.askresume.oauth.service

import app.askresume.oauth.dto.OAuthResponse
import org.springframework.http.ResponseCookie

interface OAuthCookieProvider {

    fun generateAccessTokenCookie(oAuthTokenDTO: OAuthResponse.Token): ResponseCookie

    fun generateRefreshTokenCookie(oAuthTokenDTO: OAuthResponse.Token): ResponseCookie

}