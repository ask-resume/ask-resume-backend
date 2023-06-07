package app.askresume.global.jwt.dto

import app.askresume.global.jwt.constant.JwtTokenType
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class JwtResponse {

    data class Token(
        val grantType: String,
        val tokenType: JwtTokenType,
        val token: String,
        val expirationTime: Long,

        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        val expireDate: Date,
    )

    data class TokenSet(
        val accessToken: Token,
        val refreshToken: Token,
    )

}