package app.askresume.api.member.mapper

import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.domain.member.dto.MemberInfoDto

object MyMemberMapper {

    fun memberInfoResponseOf(memberInfoDto: MemberInfoDto): MemberInfoResponse {
        return MemberInfoResponse(
            memberId = memberInfoDto.memberId,
            email = memberInfoDto.email,
            username = memberInfoDto.username,
            profile = memberInfoDto.profile,
            role = memberInfoDto.role,
            locale = memberInfoDto.locale
        )
    }
}