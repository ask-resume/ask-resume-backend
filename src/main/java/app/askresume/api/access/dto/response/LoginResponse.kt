package app.askresume.api.access.dto.response

import app.askresume.global.jwt.dto.JwtTokenDto
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class LoginResponse(

    @field:Schema(
        description = "grantType",
        example = "Bearer",
        required = true
    )
    val grantType: String,

    @field:Schema(
        description = "accessToken",
        example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2NTg0ODAyOTYsImV4cCI6MTY1ODQ4MTE5NiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.qr5fOs9NIO5UYJzqgisESOXorASLphj04uyjF1Breolj4cou_k6py0egF8f3OxWjQXps3on7Ko3jwIaL_2voRg",
        required = true
    )
    val accessToken: String,

    @field:Schema(
        description = "access token 만료 시간",
        example = "2022-07-22 18:13:16",
        required = true
    )
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val accessTokenExpireTime: Date,

    @field:Schema(
        description = "refreshToken",
        example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0aASDgwMjk3LCJleHAiOjE2NTk2ODk4OTYsIm1lbWJlcklkIjoxfQ.hxgq_DIU554lUnUCSAGTYOiaXLXwgpyIM2h8a5de3ALEOY-IokElS6VbMmVTKlpRaLfEzzcr3FkUDrNisRt-bA",
        required = true
    )
    val refreshToken: String,

    @field:Schema(
        description = "refresh token 만료 시간",
        example = "2022-08-05 18:13:16",
        required = true
    )
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val refreshTokenExpireTime: Date,

) {

    companion object {
        fun of(jwtTokenDto: JwtTokenDto): LoginResponse {
            return LoginResponse(
                grantType = jwtTokenDto.grantType,
                accessToken = jwtTokenDto.accessToken,
                accessTokenExpireTime = jwtTokenDto.accessTokenExpireTime,
                refreshToken = jwtTokenDto.refreshToken,
                refreshTokenExpireTime = jwtTokenDto.refreshTokenExpireTime,
            )
        }
    }

}