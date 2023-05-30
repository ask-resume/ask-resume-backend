package app.askresume.api.access.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class OauthLoginRequest(

    @field:Schema(
        description = "소셜 로그인 회원 타입",
        example = "GOOGLE",
        required = true
    )
    val memberType: String,

)

