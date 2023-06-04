package app.askresume.oauth.service

import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.OAuthProviderProperties
import app.askresume.oauth.constant.OAuthGrantType
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.dto.OAuthResponse
import app.askresume.oauth.dto.OAuthRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class OAuthServiceImpl(
    private val oAuthProperties: OAuthProperties,
) : OAuthService {

    /**
     *
     */
    override fun authorize(provider: OAuthProvider): URI {
        val providerProperties = getProviderProperties(provider)

        val separator = URLEncoder.encode(" ", StandardCharsets.UTF_8)
        val scopeParam = providerProperties.scope?.joinToString(separator)

        return UriComponentsBuilder
            .fromHttpUrl(providerProperties.authorizationUrl)
            .queryParam("client_id", providerProperties.clientId)
            .queryParam("response_type", "code")
            .queryParam("redirect_uri", providerProperties.redirectUrl)
            .queryParam("scope", scopeParam)
            .build()
            .toUri()
    }

    /**
     *
     */
    override fun getToken(code: String, provider: OAuthProvider): OAuthResponse.Token {
        val providerProperties = getProviderProperties(provider)
        val oauthClient = WebClient.create(providerProperties.tokenUrl)

        val tokenRequest = OAuthRequest.Token(
            grantType = OAuthGrantType.AUTHORIZATION_CODE,
            code = code,
            providerProperties = providerProperties,
        )

        return oauthClient.post()
            .uri { uriBuilder -> uriBuilder
                .queryParam("grant_type", tokenRequest.grantType)
                .queryParam("code", tokenRequest.code)
                .queryParam("client_id", tokenRequest.clientId)
                .queryParam("client_secret", tokenRequest.clientSecret)
                .queryParam("redirect_uri", tokenRequest.redirectUrl)
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .build()
            }
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(OAuthResponse.Token::class.java)
            .block()
                ?: throw Exception("Provider로부터 Access 토큰을 받아올 수 없습니다.")
    }

    /**
     *
     */
    override fun getUserInfo(accessToken: String, provider: OAuthProvider): Map<*, *> {
        val providerProperties = getProviderProperties(provider)

        val oauthClient = WebClient.create(providerProperties.userInfoUrl)

        return oauthClient.get()
            .headers { headers ->
                headers.add(HttpHeaders.AUTHORIZATION, accessToken)
            }.retrieve()
            .bodyToMono(Map::class.java)
            .block()
            ?: throw Exception("")
    }

    private fun getProviderProperties(provider: OAuthProvider): OAuthProviderProperties {
        return oAuthProperties.providers[provider]
            ?: throw Exception("Provider의 OAuth config 정보를 작성해야합니다. : $provider")
    }

}