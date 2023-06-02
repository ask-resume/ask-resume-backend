package app.askresume.oauth.service

import app.askresume.oauth.model.OAuthAttributes

interface SocialLoginApiService {

    fun getUserInfo(accessToken: String): OAuthAttributes

}

