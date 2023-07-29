package app.askresume.domain.member.service

import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.findMemberById
import app.askresume.domain.member.repository.validateDuplicateMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class MemberCommandService(
    private val memberRepository: MemberRepository,
) {

    fun registerMember(member: Member): Member {
        val email = member.email
        val memberType = member.memberType

        memberRepository.validateDuplicateMember(email, memberType)
        return memberRepository.save(member)
    }


    fun secessionMember(memberId: Long) {
        val member = memberRepository.findMemberById(memberId)
        memberRepository.delete(member)
    }

    fun expireRefreshToken(memberId: Long) {
        val member = memberRepository.findMemberById(memberId)
        member.expireRefreshToken(LocalDateTime.now())
    }

    fun modifyMemberInfo(memberId: Long, username: String, profile: String) {
        val member = memberRepository.findMemberById(memberId)
        member.changeMemberInfo(
            username = username,
            profile = profile,
        )
    }
}