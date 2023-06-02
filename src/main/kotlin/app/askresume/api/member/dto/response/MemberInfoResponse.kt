package app.askresume.api.member.dto.response

import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member
import io.swagger.v3.oas.annotations.media.Schema

data class MemberInfoResponse(
    @field:Schema(
        description = "회원 아이디",
        example = "1",
        required = true
    )
    val memberId: Long? = null,

    @field:Schema(
        description = "이메일",
        example = "test@gmail.com",
        required = true
    )
    val email: String,

    @field:Schema(
        description = "회원 이름",
        example = "홍길동",
        required = true
    )
    val username: String,

    @field:Schema(
        description = "프로필 이미지 경로",
        example = "https://domain.com/img_110x110.jpg"
    )
    val profile: String? = null,

    @Schema(description = "회원의 역할", example = "USER", required = true)
    val role: Role,

    @Schema(description = "사용자 언어", example = "EN", required = true)
    val locale: String,

) {

    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                memberId = member.id,
                email = member.email,
                username = member.username,
                profile = member.profile,
                role = member.role,
                locale = member.locale,
            )
        }
    }
}

