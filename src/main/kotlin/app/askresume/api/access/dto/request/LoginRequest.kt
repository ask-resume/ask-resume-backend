package app.askresume.api.access.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class LoginRequest(

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
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$\n",
        message = "비밀번호는 대문자, 소문자, 숫자, 특수문자 중 하나 이상을 포함해야 합니다."
    )
    @Length(min = 8)
    @NotBlank
    val password: String,

)

