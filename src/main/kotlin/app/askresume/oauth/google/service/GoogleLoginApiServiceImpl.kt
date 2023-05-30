package app.askresume.oauth.google.service

import app.askresume.domain.member.constant.MemberType
import app.askresume.global.jwt.constant.GrantType
import app.askresume.oauth.google.client.GoogleUserInfoClient
import app.askresume.oauth.google.dto.GoogleUserInfoResponse
import app.askresume.oauth.model.OAuthAttributes
import app.askresume.oauth.service.SocialLoginApiService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class GoogleLoginApiServiceImpl(
    private val googleUserInfoClient: GoogleUserInfoClient
) : SocialLoginApiService {

    override fun getUserInfo(accessToken: String): OAuthAttributes {
        val response: GoogleUserInfoResponse =
            googleUserInfoClient.getGoogleUserInfo(GrantType.BEARER.type + " " + accessToken)

        return OAuthAttributes(
            response.email,
            response.name,
            response.picture,
            response.locale,
            MemberType.GOOGLE
        )
    }
}

