package app.askresume.api.member.mapper

import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.domain.member.dto.MemberInfoDto
import app.askresume.global.annotation.Mapper

@Mapper
class MyMemberMapper {


    fun memberInfoDtoToMemberInfoResponse(memberInfoDto: MemberInfoDto): MemberInfoResponse {
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