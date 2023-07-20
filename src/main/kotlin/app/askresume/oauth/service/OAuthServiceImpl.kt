package app.askresume.oauth.service

import app.askresume.global.jwt.constant.GrantType
import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.OAuthProviderProperties
import app.askresume.oauth.client.OAuthEmailInfoClient
import app.askresume.oauth.client.OAuthTokenClient
import app.askresume.oauth.client.OAuthUserInfoClient
import app.askresume.oauth.constant.OAuthGrantType
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.constant.OAuthQueryParam
import app.askresume.oauth.dto.OAuthResponse
import app.askresume.oauth.userinfo.OAuthUserInfo
import app.askresume.oauth.userinfo.OAuthUserInfoFactory
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class OAuthServiceImpl(
    private val environment: Environment,
    private val oAuthProperties: OAuthProperties,
    private val oAuthUserInfoFactory: OAuthUserInfoFactory,
) : OAuthService {

    /**
     * 공통 Feign Client를 생성합니다.
     * TODO : 함수를 oauth 모듈 밖으로 뺄지 결정하기 (프로젝트 여기저기서 갖다쓰기 위해)
     */
    private fun <T> getOAuthClient(clazz: Class<T>, url: String): T {
        val isLocalOrDev = environment.activeProfiles.any { profile ->
            listOf("local", "dev").contains(profile)
        }

        var feignClientBuilder = Feign.builder()
            .encoder(JacksonEncoder())
            .decoder(JacksonDecoder())

        if (isLocalOrDev) {
            feignClientBuilder = feignClientBuilder
                .logger(Slf4jLogger(clazz))
                .logLevel(Logger.Level.FULL)
        }

        return feignClientBuilder
            .target(clazz, url)
    }

    /**
     * OAuth 인증을 위한 URI를 생성해 반환합니다.
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
     * OAuth 서버로부터 액세스 토큰을 가져옵니다.
     */
    override fun getToken(code: String, provider: OAuthProvider): OAuthResponse.Token {
        val providerProperties = getProviderProperties(provider)
        val oAuthTokenClient = getOAuthClient(OAuthTokenClient::class.java, providerProperties.tokenUrl)

        return oAuthTokenClient.getToken(
            tokenUrl = URI(providerProperties.tokenUrl),
            grantType = OAuthGrantType.AUTHORIZATION_CODE.value,
            code = code,
            clientId = providerProperties.clientId,
            clientSecret = providerProperties.clientSecret,
            redirectUri = providerProperties.redirectUrl,
        )
    }

    /**
     * OAuth Resource 서버로부터 유저 정보를 가져옵니다.
     */
    override fun getUserInfo(accessToken: String, provider: OAuthProvider): OAuthUserInfo {
        val providerProperties = getProviderProperties(provider)
        val oAuthUserInfoClient = getOAuthClient(OAuthUserInfoClient::class.java, providerProperties.tokenUrl)

        // Resource 서버로부터 User Info를 가져옵니다.
        val oAuthUserInfoMap = oAuthUserInfoClient.getUserInfo(
            userInfoUrl = URI(providerProperties.userInfoUrl),
            token = "${GrantType.BEARER.type} $accessToken"
        )

        // emailUrl이 별도로 있을 경우 Resource 서버로부터 email 정보를 가져옵니다. (ex: Linked In)
        providerProperties.emailUrl?.let { emailUrl ->
            val emailInfo = getEmailInfo(emailUrl, accessToken)

            emailInfo.let { oAuthUserInfoMap.putAll(emailInfo) }
        }

        return oAuthUserInfoFactory.create(provider, oAuthUserInfoMap)
    }

    private fun getEmailInfo(emailUrl: String, accessToken: String): Map<String, Any> {
        val oAuthEmailInfoClient = getOAuthClient(OAuthEmailInfoClient::class.java, emailUrl)

        return oAuthEmailInfoClient.getEmailInfo(
            emailUrl = URI(emailUrl),
            token = "${GrantType.BEARER.type} $accessToken"
        )
    }

    private fun getProviderProperties(provider: OAuthProvider): OAuthProviderProperties {
        return oAuthProperties.providers[provider]
            ?: throw Exception("Provider의 OAuth config 정보를 작성해야합니다. : $provider") // TODO
    }

}