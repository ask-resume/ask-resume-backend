package app.askresume.api.member.facade

import app.askresume.api.member.dto.request.ModifyInfoRequest
import app.askresume.api.member.dto.response.MemberInfoResponse
import app.askresume.domain.member.service.MemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MyMemberFacade(
    private val memberService: MemberService,
) {

    fun findMemberInfo(memberId: Long): MemberInfoResponse {
        val member = memberService.findMemberById(memberId)
        return MemberInfoResponse.of(member)
    }

    @Transactional
    fun modifyMemberInfo(memberId: Long, request: ModifyInfoRequest) {
        val member = memberService.findMemberById(memberId)
        memberService.modifyMemberInfo(member, request)
    }

    @Transactional
    fun secessionMember(memberId: Long) {
        val member = memberService.findMemberById(memberId)
        memberService.secessionMember(member)
    }
}

