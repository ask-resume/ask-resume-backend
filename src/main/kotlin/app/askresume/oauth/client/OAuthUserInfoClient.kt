package app.askresume.oauth.client

import feign.Headers
import feign.Param
import feign.RequestLine
import java.net.URI

interface OAuthUserInfoClient {

    @RequestLine("GET")
    @Headers(value = ["Authorization: {token}"])
    fun getUserInfo(
        userInfoUrl: URI,
        @Param("token") token: String,
    ): MutableMap<String, Any>

}