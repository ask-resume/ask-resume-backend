package app.askresume.api.access.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

data class OauthLoginRequest(

    @field:Schema(
        description = "소셜 로그인 회원 타입",
        example = "GOOGLE",
        required = true,
    )
    @field:NotBlank
    val memberType: String,

)

