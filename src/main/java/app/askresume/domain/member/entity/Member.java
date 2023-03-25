package app.askresume.domain.member.entity;

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
@Where(clause = "status = 'Y'")
@SQLDelete(sql = "UPDATE member SET status = 'N' WHERE member_id = ?")
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "iduk", columnNames = {"email", "memberType"})})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MemberType memberType;
    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 300)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @ColumnDefault("'Y'")
    @Column(length = 1, nullable = false)
    private String status;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    public void changeMemberInfo(String username, String profile) {
        this.username = username;
        this.profile = profile;
    }
}
