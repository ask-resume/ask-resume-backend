package app.askresume.oauth.model;

import app.askresume.domain.member.constant.MemberType;
import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;

    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {
        return Member.builder()
                .username(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }

}
