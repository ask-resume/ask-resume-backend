package app.askresume.api.access.usecase

import app.askresume.domain.member.service.MemberCommandService
import app.askresume.domain.member.service.MemberReadOnlyService
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.jwt.service.TokenManager
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AccessUseCase(
    private val memberCommandService: MemberCommandService,
    private val memberReadOnlyService: MemberReadOnlyService,
    private val tokenManager: TokenManager,
) {

    val log = logger()

    fun logout(accessToken: String) {
        // 토큰 검증
        tokenManager.validateToken(accessToken)

        // refresh token 만료 처리
        val memberId = tokenManager.getMemberIdFromAccessToken(accessToken)
        memberCommandService.expireRefreshToken(memberId)
    }

    fun createAccessTokenByRefreshToken(refreshToken: String): JwtResponse.Token {
        val memberInfoDto = memberReadOnlyService.findMemberByRefreshToken(refreshToken)
        return tokenManager.createAccessToken(memberInfoDto.memberId, memberInfoDto.role)
    }

}

