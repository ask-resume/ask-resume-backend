package app.askresume.oauth.model;

import app.askresume.domain.member.constant.MemberType;
import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;

public record OAuthAttributes(
        String email,
        String name,
        String profile,
        String locale,
        MemberType memberType
) {
    public OAuthAttributes(String email, String name, String profile, String locale, MemberType memberType) {
        this.email = email;
        this.name = name;
        this.profile = profile;
        this.locale = locale;
        this.memberType = memberType;
    }

    public Member toMemberEntity(MemberType memberType, Role role) {
        return Member.builder()
                .username(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .locale(locale)
                .role(role)
                .build();
    }

}
