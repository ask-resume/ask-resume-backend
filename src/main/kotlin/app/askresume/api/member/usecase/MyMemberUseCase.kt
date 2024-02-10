package app.askresume.api.member.usecase

import app.askresume.api.member.mapper.MyMemberMapper
import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.api.member.vo.ModifyInfoRequest
import app.askresume.domain.member.service.MemberCommandService
import app.askresume.domain.member.service.MemberReadOnlyService
import app.askresume.global.annotation.UseCase
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(readOnly = true)
class MyMemberUseCase(
    private val memberReadOnlyService: MemberReadOnlyService,
    private val memberCommandService: MemberCommandService,

    private val myMemberMapper: MyMemberMapper,
) {

    fun findMyMemberInfo(memberId: Long): MemberInfoResponse {
        val memberInfoDto = memberReadOnlyService.findMemberInfo(memberId) !!
        return myMemberMapper.memberInfoResponseOf(memberInfoDto)
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

