package app.askresume.global.jwt.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class JwtResponse {

    data class TokenDto(
        val grantType: String,

        val accessToken: String,

        val accessTokenExpirationTime: Long,

        @field:JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
        )
        val accessTokenExpireDate: Date,

        val refreshToken: String,

        val refreshTokenExpirationTime: Long,

        @field:JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
        )
        val refreshTokenExpireDate: Date,
    )

}