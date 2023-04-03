package app.askresume.api.access.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record LoginRequest(
        @Email
        @NotBlank
        @Schema(description = "이메일", example = "member@domain.co.kr", required = true)
        String email,

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$\n", message = "비밀번호는 대문자, 소문자, 숫자, 특수문자 중 하나 이상을 포함해야 합니다.")
        @Length(min = 8)
        @NotBlank
        @Schema(description = "비밀번호", example = "!Password123!", required = true)
        String password
) {

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
