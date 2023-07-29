package app.askresume.api.member.vo

import app.askresume.domain.member.constant.Role
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ModifyInfoRequest(
    @field:Schema(
        description = "회원 이름",
        example = "홍길동",
        required = true
    )
    @field:NotBlank
    @field:Size(min = 2, max = 20)
    val username: String,

    @field:Schema(
        description = "프로필 이미지 경로",
        example = "https://domain.com/img_110x110.jpg",
    )
    @field:URL(message = "올바른 URL 형식이 아닙니다.")
    val profile: String,
)


data class MemberInfoResponse(
    @field:Schema(
        description = "회원 아이디", example = "1", required = true
    ) val memberId: Long? = null,

    @field:Schema(
        description = "이메일", example = "test@gmail.com", required = true
    ) val email: String,

    @field:Schema(
        description = "회원 이름", example = "홍길동", required = true
    ) val username: String,

    @field:Schema(
        description = "프로필 이미지 경로", example = "https://domain.com/img_110x110.jpg"
    ) val profile: String? = null,

    @field:Schema(description = "회원의 역할", example = "USER", required = true) val role: Role,

    @field:Schema(description = "사용자 언어", example = "EN", required = true) val locale: String,
)