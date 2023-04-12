package app.askresume.global.resolver.memberinfo;

import app.askresume.domain.member.constant.Role;

public record MemberInfoDto(
        Long memberId,
        Role role
) {
}
