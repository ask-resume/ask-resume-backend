package app.askresume.domain.member.service

import app.askresume.api.member.dto.request.ModifyInfoRequest
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.exception.DuplicateMemberException
import app.askresume.domain.member.exception.MemberNotFoundException
import app.askresume.domain.member.exception.RefreshTokenExpiredException
import app.askresume.domain.member.exception.RefreshTokenNotFoundException
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.findMemberById
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun registerMember(member: Member): Member {
        val email = member.email
        val memberType = member.memberType

        validateDuplicateMember(email, memberType)
        return memberRepository.save(member)
    }

    // 이메일 체크
    private fun validateDuplicateMember(email: String, memberType: MemberType) {
        if(memberRepository.existsByEmailAndMemberType(email, memberType))
            throw DuplicateMemberException(email, memberType.name)

    }

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

    fun findMemberById(memberId: Long): Member {
        return memberRepository.findMemberById(memberId)
    }

    @Transactional
    fun modifyMemberInfo(member: Member, request: ModifyInfoRequest) {
        member.changeMemberInfo(request.username, request.profile)
    }

    @Transactional
    fun secessionMember(member: Member) {
        memberRepository.delete(member)
    }

}
