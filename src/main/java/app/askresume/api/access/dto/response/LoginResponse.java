package app.askresume.api.access.dto.response;

import app.askresume.global.jwt.dto.JwtTokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record LoginResponse(
        @Schema(description = "grantType", example = "Bearer", required = true)
        String grantType,

        @Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2NTg0ODAyOTYsImV4cCI6MTY1ODQ4MTE5NiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.qr5fOs9NIO5UYJzqgisESOXorASLphj04uyjF1Breolj4cou_k6py0egF8f3OxWjQXps3on7Ko3jwIaL_2voRg", required = true)
        String accessToken,

        @Schema(description = "access token 만료 시간", example = "2022-07-22 18:13:16", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        Date accessTokenExpireTime,

        @Schema(description = "refreshToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0aASDgwMjk3LCJleHAiOjE2NTk2ODk4OTYsIm1lbWJlcklkIjoxfQ.hxgq_DIU554lUnUCSAGTYOiaXLXwgpyIM2h8a5de3ALEOY-IokElS6VbMmVTKlpRaLfEzzcr3FkUDrNisRt-bA", required = true)
        String refreshToken,

        @Schema(description = "refresh token 만료 시간", example = "2022-08-05 18:13:16", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        Date refreshTokenExpireTime
) {

    public LoginResponse(String grantType, String accessToken, Date accessTokenExpireTime, String refreshToken, Date refreshTokenExpireTime) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public static LoginResponse of(JwtTokenDto jwtTokenDto) {
        return new LoginResponse(
                jwtTokenDto.grantType(),
                jwtTokenDto.accessToken(),
                jwtTokenDto.accessTokenExpireTime(),
                jwtTokenDto.refreshToken(),
                jwtTokenDto.refreshTokenExpireTime()
        );
    }

}