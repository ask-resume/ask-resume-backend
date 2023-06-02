package app.askresume.api.access.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class AccessTokenResponse(

    @field:Schema(description = "grantType", example = "Bearer", required = true)
    val grantType: String,

    @field:Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIU.....", required = true)
    val accessToken: String,

    @field:Schema(description = "access token 만료 시간", example = "2022-07-22 18:13:16", required = true)
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul",
    )
    val accessTokenExpireTime: Date,
)

