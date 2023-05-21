package app.askresume.domain.member.model;

import app.askresume.domain.common.BaseTimeEntity;
import app.askresume.domain.member.constant.MemberType;
import app.askresume.domain.member.constant.Role;
import app.askresume.global.jwt.dto.JwtTokenDto;
import app.askresume.global.util.DateTimeUtils;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE member SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "iduk", columnNames = {"email", "memberType"})})
public class Member extends BaseTimeEntity {

    @Comment(value = "이메일")
    @Column(length = 50, nullable = false)
    private String email;

    @Comment(value = "비밀번호")
    @Column(length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Comment(value = "맴버 타입")
    @Column(length = 10, nullable = false)
    private MemberType memberType;

    @Comment(value = "사용자명")
    @Column(length = 30, nullable = false)
    private String username;

    @Comment(value = "사용자언어")
    @Column(length = 5, nullable = false)
    private String locale;

    @Comment(value = "프로필")
    @Column(length = 300)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Comment(value = "권한")
    @Column(length = 10, nullable = false)
    private Role role;

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    private String deletedAt;

    @Comment(value = "토큰")
    @Column(length = 250)
    private String refreshToken;

    @Comment(value = "토큰 만료일")
    private LocalDateTime tokenExpirationTime;

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.refreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.refreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    public void changeMemberInfo(String username, String profile) {
        this.username = username;
        this.profile = profile;
    }
}
