package app.askresume.api.member.dto.request

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
    @NotBlank
    @Size(min = 2, max = 20)
    val username: String,

    @field:Schema(
        description = "프로필 이미지 경로",
        example = "https://domain.com/img_110x110.jpg"
    )
    @URL(message = "올바른 URL 형식이 아닙니다.")
    val profile: String,

)

