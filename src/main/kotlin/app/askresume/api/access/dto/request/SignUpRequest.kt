package app.askresume.api.access.dto.request

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member
import app.askresume.global.util.SHA256Util
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequest(

    @field:Schema(
        description = "이메일",
        example = "member@domain.co.kr",
        required = true
    )
    @Email
    @NotBlank
    val email: String,

    @field:Schema(
        description = "비밀번호",
        example = "!Password123!",
        required = true
    )
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "비밀번호는 대문자, 소문자, 숫자, 특수문자 중 하나 이상을 포함해야 합니다."
    )
    @Length(min = 8)
    @NotBlank
    val password: String,

    @field:Schema(
        description = "비밀번호 확인",
        example = "!Password123!",
        required = true
    )
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "비밀번호는 대문자, 소문자, 숫자, 특수문자 중 하나 이상을 포함해야 합니다."
    )
    @Length(min = 8)
    @NotBlank
    val passwordCheck: String,

    @field:Schema(
        description = "회원 이름",
        example = "홍길동",
        required = true
    )
    @NotBlank
    @Size(min = 2, max = 20)
    val username: String,

    @field:Schema(
        description = "프로필 이미지 경로",
        example = "https://domain.com/img_110x110.jpg"
    )
    @URL(message = "올바른 URL 형식이 아닙니다.")
    val profile: String,

    @field:Schema(
        description = "언어",
        example = "en"
    )
    @NotBlank
    val locale: String,

) {

    fun toMemberEntity(memberType: MemberType, role: Role): Member {
        return Member(
            email = email,
            password = SHA256Util.encrypt(password),
            username = username,
            profile = profile,
            memberType = memberType,
            locale = locale,
            role = role,
        )
    }
    
}

