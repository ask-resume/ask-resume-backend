package app.askresume.api.member.facade

import app.askresume.api.member.dto.request.ModifyInfoRequest
import app.askresume.api.member.dto.response.MemberInfoResponse
import app.askresume.domain.member.service.MemberCommandService
import app.askresume.domain.member.service.MemberReadOnlyService
import app.askresume.global.annotation.Facade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Facade
@Transactional(readOnly = true)
class MyMemberFacade(
    private val memberReadOnlyService: MemberReadOnlyService,
    private val memberCommandService: MemberCommandService,
) {

    fun findMemberInfo(memberId: Long): MemberInfoResponse {
        val member = memberReadOnlyService.findMemberById(memberId)
        return MemberInfoResponse.of(member)
    }

    @Transactional
    fun modifyMemberInfo(memberId: Long, request: ModifyInfoRequest) {
        val member = memberReadOnlyService.findMemberById(memberId)
        memberCommandService.modifyMemberInfo(member, request)
    }

    @Transactional
    fun secessionMember(memberId: Long) {
        val member = memberReadOnlyService.findMemberById(memberId)
        memberCommandService.secessionMember(member)
    }
}

