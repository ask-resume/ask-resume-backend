package app.askresume.api.member.dto.response;

import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
        @Schema(description = "회원 아이디", example = "1", required = true)
        Long memberId,

        @Schema(description = "이메일", example = "test@gmail.com", required = true)
        String email,

        @Schema(description = "회원 이름", example = "홍길동", required = true)
        String username,

        @Schema(description = "프로필 이미지 경로", example = "https://domain.com/img_110x110.jpg")
        String profile,

        @Schema(description = "회원의 역할", example = "USER", required = true)
        Role role,

        @Schema(description = "사용자 언어", example = "EN", required = true)
        String locale
) {

    public MemberInfoResponse(Long memberId, String email, String username, String profile, Role role, String locale) {
        this.memberId = memberId;
        this.email = email;
        this.username = username;
        this.profile = profile;
        this.role = role;
        this.locale = locale;
    }

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail(), member.getUsername(), member.getProfile(), member.getRole(), member.getLocale());
    }
}
