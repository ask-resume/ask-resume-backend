package app.askresume.oauth.service

import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.dto.OAuthResponse
import java.net.URI

interface OAuthService {

    fun authorize(provider: OAuthProvider): URI

    fun getToken(code: String, provider: OAuthProvider): OAuthResponse.Token

    fun getUserInfo(accessToken: String, provider: OAuthProvider) : Map<*, *>

}