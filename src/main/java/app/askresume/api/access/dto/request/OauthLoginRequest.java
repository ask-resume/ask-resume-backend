package app.askresume.api.access.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
public class OauthLoginRequest {

    @Schema(description = "소셜 로그인 회원 타입", example = "KAKAO", required = true)
    private String memberType;

}


