package app.askresume.api.access.facade

import app.askresume.api.access.dto.request.LoginRequest
import app.askresume.api.access.dto.request.SignUpRequest
import app.askresume.api.access.dto.response.AccessTokenResponse
import app.askresume.api.access.dto.response.LoginResponse
import app.askresume.api.member.validator.PasswordValidator
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.service.MemberService
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.AuthenticationException
import app.askresume.global.jwt.constant.JwtGrantType
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.jwt.service.TokenManager
import app.askresume.global.util.LoggerUtil.log
import app.askresume.global.util.SHA256Util
import app.askresume.oauth.service.SocialLoginApiServiceFactory
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


@Service
@Transactional(readOnly = true)
class AccessFacade(
    private val memberService: MemberService,
    private val tokenManager: TokenManager,
    private val socialLoginApiServiceFactory: SocialLoginApiServiceFactory,
) {

    val log = log()

    @Transactional
    fun register(signUpRequest: SignUpRequest) {
        PasswordValidator.passwordCheck(signUpRequest.password, signUpRequest.passwordCheck)

        val member = signUpRequest.toMemberEntity(MemberType.LOCAL, Role.USER)
        memberService.registerMember(member)
    }

    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        val email = loginRequest.email
        val encryptPassword = SHA256Util.encrypt(loginRequest.password)
        val memberType = MemberType.LOCAL

        val member: Member = memberService.findMemberByEmailAndPasswordAndMemberType(email, encryptPassword, memberType)
        val jwtTokenDto = tokenManager.createJwtTokenDto(member.id, member.role)
        member.updateRefreshToken(jwtTokenDto)

        return LoginResponse.of(jwtTokenDto)
    }

    @Transactional
    fun oauthLogin(accessToken: String, memberType: MemberType): LoginResponse {
        val socialLoginApiService = socialLoginApiServiceFactory.getSocialLoginApiService(memberType)
        val userInfo = socialLoginApiService.getUserInfo(accessToken)
        log.debug(userInfo.toString())

        val jwtTokenDto: JwtResponse.TokenDto
        val member = memberService.findMemberByEmail(userInfo.email, memberType)

        var oauthMember: Member
        if (member == null) { // 신규 회원 가입
            oauthMember = userInfo.toMemberEntity(memberType, Role.USER)
            oauthMember = memberService.registerMember(oauthMember)
        } else { // 기존 회원일 경우
            oauthMember = member
        }

        // 토큰 생성
        jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.id, oauthMember.role)
        oauthMember.updateRefreshToken(jwtTokenDto)

        return LoginResponse.of(jwtTokenDto)
    }

    fun logout(accessToken: String) {

        // 1. 토큰 검증
        tokenManager.validateToken(accessToken)

        // 2. 토큰 타입 확인
        val tokenClaims: Claims = tokenManager.getTokenClaims(accessToken)
        val tokenType = tokenClaims.subject

        if (!JwtTokenType.isAccessToken(tokenType)) {
            throw AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE)
        }

        // 3. refresh token 만료 처리
        val memberId = (tokenClaims["memberId"] as Int?)?.toLong()
        val member: Member = memberService.findMemberById(memberId)
        member.expireRefreshToken(LocalDateTime.now())
    }

    fun createAccessTokenByRefreshToken(refreshToken: String): AccessTokenResponse {
        val member: Member = memberService.findMemberByRefreshToken(refreshToken)

        val accessTokenExpireTime: Date = tokenManager.createAccessTokenExpireTime()
        val accessToken: String = tokenManager.createAccessToken(member.id, member.role, accessTokenExpireTime)

        return AccessTokenResponse(
            grantType = JwtGrantType.BEARER.type,
            accessToken = accessToken,
            accessTokenExpireTime = accessTokenExpireTime,
        )
    }
}

