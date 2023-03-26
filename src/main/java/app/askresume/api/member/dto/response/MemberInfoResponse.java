package app.askresume.api.member.dto.response;

import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInfoResponse {

    @Schema(description = "회원 아이디", example = "1", required = true)
    private Long memberId;

    @Schema(description = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @Schema(description = "회원 이름", example = "홍길동", required = true)
    private String username;

    @Schema(description = "프로필 이미지 경로", example = "https://domain.com/img_110x110.jpg")
    private String profile;

    @Schema(description = "회원의 역할", example = "USER", required = true)
    private Role role;

    public static MemberInfoResponse of(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .profile(member.getProfile())
                .role(member.getRole())
                .build();
    }
}
