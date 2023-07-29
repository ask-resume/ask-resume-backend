package app.askresume.domain.member.service

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.dto.MemberInfoDto
import app.askresume.domain.member.exception.RefreshTokenExpiredException
import app.askresume.domain.member.exception.RefreshTokenNotFoundException
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberQueryRepository
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.findMemberById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class MemberReadOnlyService(
    private val memberRepository: MemberRepository,
    private val memberQueryRepository: MemberQueryRepository,
) {

    fun findMemberByEmail(email: String, memberType: MemberType): Member? {
        return memberRepository.findByEmailAndMemberType(email, memberType)
    }

    fun findMemberByRefreshToken(refreshToken: String): Member {
        val member: Member = memberRepository.findByRefreshToken(refreshToken)
            ?: throw RefreshTokenNotFoundException()

        val tokenExpirationTime = member.tokenExpirationTime

        if (tokenExpirationTime?.isBefore(LocalDateTime.now()) == true) { // DB 타입이 nullable해서 null safety 구문 추가
            throw RefreshTokenExpiredException()
        }
        return member
    }

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

}
