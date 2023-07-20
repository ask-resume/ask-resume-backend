package app.askresume.oauth.client

import app.askresume.oauth.dto.OAuthResponse
import feign.Headers
import feign.Param
import feign.RequestLine
import java.net.URI

interface OAuthTokenClient {

    @RequestLine("POST ?grant_type={grantType}&code={code}&client_id={clientId}&client_secret={clientSecret}&redirect_uri={redirectUri}")
    @Headers("Content-Type: application/json")
    fun getToken(
        tokenUrl: URI,
        @Param grantType: String,
        @Param code: String,
        @Param clientId: String,
        @Param clientSecret: String,
        @Param redirectUri: String,
    ): OAuthResponse.Token

}