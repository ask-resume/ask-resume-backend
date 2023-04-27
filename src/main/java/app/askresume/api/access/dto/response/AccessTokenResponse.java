package app.askresume.api.access.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record AccessTokenResponse (
    @Schema(description = "grantType", example = "Bearer", required = true)
    String grantType,

    @Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2NTg0ODAyOTYsImV4cCI6MTY1ODQ4MTE5NiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.qr5fOs9NIO5UYJzqgisESOXorASLphj04uyjF1Breolj4cou_k6py0egF8f3OxWjQXps3on7Ko3jwIaL_2voRg", required = true)
    String accessToken,

    @Schema(description = "access token 만료 시간", example = "2022-07-22 18:13:16", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    Date accessTokenExpireTime
){
    public AccessTokenResponse(String grantType, String accessToken, Date accessTokenExpireTime) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
    }
}
