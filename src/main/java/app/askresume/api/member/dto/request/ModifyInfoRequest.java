package app.askresume.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ModifyInfoRequest {

    @Schema(description = "회원 이름", example = "홍길동", required = true)
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @Pattern(regexp="^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$", message="올바른 URL 형식이 아닙니다.")
    @Schema(description = "프로필 이미지 경로", example = "https://domain.com/img_110x110.jpg")
    private String profile;


    public ModifyInfoRequest(String username, String profile) {
        this.username = username;
        this.profile = profile;
    }
}
