package app.askresume.api.access.facade

import app.askresume.api.access.dto.request.LoginRequest
import app.askresume.api.access.dto.request.SignUpRequest
import app.askresume.api.member.validator.PasswordValidator
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.service.MemberService
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.jwt.service.TokenManager
import app.askresume.global.util.LoggerUtil.log
import app.askresume.global.util.SHA256Util
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class AccessFacade(
    private val memberService: MemberService,
    private val tokenManager: TokenManager,
) {

    val log = log()

    @Transactional
    fun register(signUpRequest: SignUpRequest) {
        PasswordValidator.passwordCheck(signUpRequest.password, signUpRequest.passwordCheck)

        val member = signUpRequest.toMemberEntity(MemberType.LOCAL, Role.USER)
        memberService.registerMember(member)
    }

    @Transactional
    fun login(loginRequest: LoginRequest): JwtResponse.TokenSet {
        val email = loginRequest.email
        val encryptPassword = SHA256Util.encrypt(loginRequest.password)
        val memberType = MemberType.LOCAL

        val member = memberService.findMemberByEmailAndPasswordAndMemberType(email, encryptPassword, memberType)
        val jwtTokenSet = tokenManager.createJwtTokenSet(member.id, member.role)
        member.updateRefreshToken(
            refreshToken = jwtTokenSet.refreshToken.token,
            expireDate = jwtTokenSet.refreshToken.expireDate
        )

        return jwtTokenSet
    }

    fun logout(accessToken: String) {
        // 토큰 검증
        tokenManager.validateToken(accessToken)

        // refresh token 만료 처리
        val memberId = tokenManager.getMemberIdFromAccessToken(accessToken)
        val member = memberService.findMemberById(memberId)
        member.expireRefreshToken(LocalDateTime.now())
    }

    fun createAccessTokenByRefreshToken(refreshToken: String): JwtResponse.Token {
        val member = memberService.findMemberByRefreshToken(refreshToken)
        return tokenManager.createAccessToken(member.id, member.role)
    }

}

