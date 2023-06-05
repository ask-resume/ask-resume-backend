package app.askresume.oauth.service

import app.askresume.global.jwt.constant.JwtGrantType
import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.OAuthProviderProperties
import app.askresume.oauth.constant.OAuthGrantType
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.constant.OAuthQueryParam
import app.askresume.oauth.dto.OAuthResponse
import app.askresume.oauth.userinfo.OAuthUserInfo
import app.askresume.oauth.userinfo.OAuthUserInfoFactory
import org.springframework.core.ParameterizedTypeReference
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
    private val oAuthUserInfoFactory: OAuthUserInfoFactory,
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
            .queryParam(OAuthQueryParam.CLIENT_ID.key, providerProperties.clientId)
            .queryParam(OAuthQueryParam.RESPONSE_TYPE.key, "code")
            .queryParam(OAuthQueryParam.REDIRECT_URI.key, providerProperties.redirectUrl)
            .queryParam(OAuthQueryParam.SCOPE.key, scopeParam)
            .build()
            .toUri()
    }

    /**
     *
     */
    override fun getToken(code: String, provider: OAuthProvider): OAuthResponse.Token {
        val providerProperties = getProviderProperties(provider)
        val oAuthClient = WebClient.create(providerProperties.tokenUrl)

        return oAuthClient.post()
            .uri { uriBuilder -> uriBuilder
                .queryParam(OAuthQueryParam.GRANT_TYPE.key, OAuthGrantType.AUTHORIZATION_CODE)
                .queryParam(OAuthQueryParam.CODE.key, code)
                .queryParam(OAuthQueryParam.CLIENT_ID.key, providerProperties.clientId)
                .queryParam(OAuthQueryParam.CLIENT_SECRET.key, providerProperties.clientSecret)
                .queryParam(OAuthQueryParam.REDIRECT_URI.key, providerProperties.redirectUrl)
                .build()
            }
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .retrieve()
            .bodyToMono(OAuthResponse.Token::class.java)
            .block()
                ?: throw Exception("Provider로부터 Access 토큰을 받아올 수 없습니다.") // TODO
    }

    /**
     *
     */
    override fun getUserInfo(accessToken: String, provider: OAuthProvider): OAuthUserInfo {
        val providerProperties = getProviderProperties(provider)

        val oAuthClient = WebClient.create(providerProperties.userInfoUrl)

        // Resource 서버로부터 User Info를 가져옵니다.
        val oAuthUserInfoMap = oAuthClient.get()
            .headers { headers ->
                headers.add(HttpHeaders.AUTHORIZATION, "${JwtGrantType.BEARER.type} $accessToken")
            }.retrieve()
            .bodyToMono(object : ParameterizedTypeReference<MutableMap<String, Any>>() {})
            .block()
            ?: throw Exception("") // TODO

        // emailUrl이 별도로 있을 경우 Resource 서버로부터 email 정보를 가져옵니다. (ex: Linked In)
        providerProperties.emailUrl?.let { emailUrl ->
            val emailInfo = getEmailInfo(emailUrl, accessToken)

            emailInfo?.let { oAuthUserInfoMap.putAll(emailInfo) }
        }

        return oAuthUserInfoFactory.create(provider, oAuthUserInfoMap)
    }

    private fun getEmailInfo(emailUrl: String, accessToken: String): Map<String, Any>? {
        val oAuthClient = WebClient.create(emailUrl)

        return oAuthClient.get()
            .headers { headers ->
                headers.add(HttpHeaders.AUTHORIZATION, "${JwtGrantType.BEARER.type} $accessToken")
            }.retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, Any>>() {})
            .block()
    }

        private fun getProviderProperties(provider: OAuthProvider): OAuthProviderProperties {
        return oAuthProperties.providers[provider]
            ?: throw Exception("Provider의 OAuth config 정보를 작성해야합니다. : $provider") // TODO
    }

}