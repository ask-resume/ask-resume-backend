package app.askresume.domain.member.service

import app.askresume.api.member.dto.request.ModifyInfoRequest
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.validateDuplicateMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun modifyMemberInfo(member: Member, request: ModifyInfoRequest) {
        member.changeMemberInfo(request.username, request.profile)
    }

    fun secessionMember(member: Member) {
        memberRepository.delete(member)
    }

}