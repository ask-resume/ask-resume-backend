package app.askresume.global.jwt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record JwtTokenDto(
        String grantType,

        String accessToken,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        Date accessTokenExpireTime,

        String refreshToken,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        Date refreshTokenExpireTime
) {

    public JwtTokenDto(String grantType, String accessToken, Date accessTokenExpireTime, String refreshToken, Date refreshTokenExpireTime) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}
