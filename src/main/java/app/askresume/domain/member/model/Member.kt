package app.askresume.domain.member.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.global.jwt.dto.JwtTokenDto
import app.askresume.global.util.DateTimeUtils
import org.hibernate.annotations.*
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE member SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "iduk", columnNames = ["email", "memberType"])])
class Member(

    @Comment(value = "이메일")
    @Column(length = 50, nullable = false)
    val email: String,

    @Comment(value = "비밀번호")
    @Column(length = 200)
    val password: String?,

    @Enumerated(EnumType.STRING)
    @Comment(value = "맴버 타입")
    @Column(length = 10, nullable = false)
    val memberType: MemberType,

    @Comment(value = "사용자언어")
    @Column(length = 5, nullable = false)
    val locale: String,

    @Enumerated(EnumType.STRING)
    @Comment(value = "권한")
    @Column(length = 10, nullable = false)
    val role: Role,

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    val deletedAt: String,

    username: String,
    profile: String? = null,
    refreshToken: String? = null,
    tokenExpirationTime: LocalDateTime? = null,

) : BaseTimeEntity() {

    @Comment(value = "사용자명")
    @Column(length = 30, nullable = false)
    var username: String = username
        protected set

    @Comment(value = "프로필")
    @Column(length = 300)
    var profile: String? = profile
        protected set

    @Comment(value = "토큰")
    @Column(length = 250)
    var refreshToken: String? = null
        protected set

    @Comment(value = "토큰 만료일")
    var tokenExpirationTime: LocalDateTime? = tokenExpirationTime
        protected set

    fun updateRefreshToken(jwtTokenDto: JwtTokenDto) {
        refreshToken = jwtTokenDto.refreshToken
        tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.refreshTokenExpireTime)
    }

    fun expireRefreshToken(now: LocalDateTime?) {
        tokenExpirationTime = now
    }

    fun changeMemberInfo(username: String, profile: String) {
        this.username = username
        this.profile = profile
    }

}
