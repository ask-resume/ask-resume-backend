package app.askresume.api.access.facade

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.service.MemberCommandService
import app.askresume.domain.member.service.MemberReadOnlyService
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.jwt.service.TokenManager
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.service.OAuthService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI

@Service
@Transactional(readOnly = true)
class OAuthFacade(
    private val tokenManager: TokenManager,
    private val memberReadOnlyService: MemberReadOnlyService,
    private val memberCommandService: MemberCommandService,
    private val oAuthService: OAuthService,
) {

    fun getAuthorizationUri(provider: OAuthProvider): URI {
        return oAuthService.authorize(provider)
    }

    @Transactional
    fun joinOrLogin(code: String, provider: OAuthProvider): JwtResponse.TokenSet {
        // OAuth Access 토큰을 얻어옵니다.
        val oAuthTokenDto = oAuthService.getToken(code, provider)
        // OAuth Access 토큰으로 OAuth UserInfo를 얻어옵니다.
        val userInfo = oAuthService.getUserInfo(oAuthTokenDto.accessToken, provider)

        // 해당 email과 provider 정보로 가입된 회원이 있는지 체크합니다.
        val memberType = MemberType.from(provider.name)
        val member = memberReadOnlyService.findMemberByEmail(userInfo.email, memberType)

        // 가입된 회원 정보가 있으면 로그인을, 없으면 회원가입을 진행합니다.
        val oAuthMember = member?.let { // 로그인
            member
        } ?: run { // 회원가입
            val newMember = userInfo.toMemberEntity(memberType, Role.USER)
            memberCommandService.registerMember(newMember)
        }

        // 토큰 생성
        val jwtTokenSet = tokenManager.createJwtTokenSet(oAuthMember.id, oAuthMember.role)
        oAuthMember.updateRefreshToken(
            refreshToken = jwtTokenSet.refreshToken.token,
            expireDate = jwtTokenSet.refreshToken.expireDate
        )

        return jwtTokenSet
    }

}