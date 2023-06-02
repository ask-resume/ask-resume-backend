package app.askresume.global.jwt.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class JwtTokenDto(
    val grantType: String,

    val accessToken: String,

    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val accessTokenExpireTime: Date,

    val refreshToken: String,

    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val refreshTokenExpireTime: Date,
)

