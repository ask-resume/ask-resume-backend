package app.askresume.api.access.facade

import app.askresume.domain.member.service.MemberService
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.jwt.service.TokenManager
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class AccessFacade(
    private val memberService: MemberService,
    private val tokenManager: TokenManager,
) {

    val log = logger()

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

