package app.askresume.oauth.client

import feign.Headers
import feign.Param
import feign.RequestLine
import java.net.URI

interface OAuthEmailInfoClient {

    @RequestLine("GET")
    @Headers(value = ["Authorization: {token}"])
    fun getEmailInfo(
        emailUrl: URI,
        @Param("token") token: String,
    ): Map<String, Any>

}