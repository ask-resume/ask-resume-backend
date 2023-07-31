package app.askresume.domain.member.service

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.dto.MemberInfoDto
import app.askresume.domain.member.exception.RefreshTokenExpiredException
import app.askresume.domain.member.exception.RefreshTokenNotFoundException
import app.askresume.domain.member.repository.MemberQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class MemberReadOnlyService(
    private val memberQueryRepository: MemberQueryRepository,
) {

    fun findMemberInfo(
        memberId: Long? = null,
        email: String? = null,
        memberType: MemberType? = null,
    ): MemberInfoDto? {
        return memberQueryRepository.findQueryMemberInfo(
            memberId = memberId,
            email = email,
            memberType = memberType,
        )
    }

    fun findMemberByEmail(email: String, memberType: MemberType): MemberInfoDto? {
        return memberQueryRepository.findQueryMemberInfo(
            email = email,
            memberType = memberType,
        )
    }

    fun findMemberByRefreshToken(refreshToken: String): MemberInfoDto {
        val memberInfoDto = (memberQueryRepository.findQueryMemberInfo(refreshToken = refreshToken)
            ?: throw RefreshTokenNotFoundException())

        val tokenExpirationTime = memberInfoDto.tokenExpirationTime

        if (tokenExpirationTime?.isBefore(LocalDateTime.now()) == true) { // DB 타입이 nullable해서 null safety 구문 추가
            throw RefreshTokenExpiredException()
        }
        return memberInfoDto
    }

}
