package app.askresume.api.member.facade

import app.askresume.api.member.mapper.MyMemberMapper
import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.api.member.vo.ModifyInfoRequest
import app.askresume.domain.member.service.MemberCommandService
import app.askresume.domain.member.service.MemberReadOnlyService
import app.askresume.global.annotation.Facade
import org.springframework.transaction.annotation.Transactional

@Facade
@Transactional(readOnly = true)
class MyMemberFacade(
    private val memberReadOnlyService: MemberReadOnlyService,
    private val memberCommandService: MemberCommandService,

    private val myMemberMapper: MyMemberMapper,
) {

    fun findMyMemberInfo(memberId: Long): MemberInfoResponse {
        val memberInfoDto = memberReadOnlyService.findMemberInfo(memberId) !!
        return myMemberMapper.memberInfoDtoToMemberInfoResponse(memberInfoDto)
    }

    @Transactional
    fun modifyMyMemberInfo(memberId: Long, request: ModifyInfoRequest) {
        val memberInfoDto = memberReadOnlyService.findMemberInfo(memberId) !!
        memberCommandService.modifyMemberInfo(
            memberId = memberInfoDto.memberId,
            username = request.username,
            profile = request.profile,
        )
    }

    @Transactional
    fun secessionMember(memberId: Long) {
        memberCommandService.secessionMember(memberId)
    }
}

