package app.askresume.domain.member.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.global.util.DateTimeUtils
import org.hibernate.annotations.*
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id = ?")
@Table(uniqueConstraints = [UniqueConstraint(name = "unique_email_member_type", columnNames = ["email", "memberType"])])
class Member(

    @Comment(value = "이메일")
    @Column(length = 50, nullable = false)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Comment(value = "맴버 타입")
    @Column(length = 10, nullable = false)
    val memberType: MemberType,

    locale: String,
    role: Role,
    username: String,
    profile: String? = null,
    refreshToken: String? = null,
    tokenExpirationTime: LocalDateTime? = null,

    ) : BaseTimeEntity() {

    @Comment(value = "사용자 국가")
    @Column(length = 5, nullable = false)
    var locale = locale
        protected set

    @Enumerated(EnumType.STRING)
    @Comment(value = "권한")
    @Column(length = 10, nullable = false)
    var role = role
        protected set

    @Comment(value = "사용자명")
    @Column(length = 30, nullable = false)
    var username: String = username
        protected set

    @Comment(value = "프로필")
    @Column(length = 300)
    var profile: String? = profile
        protected set

    @Comment(value = "Refresh Token")
    @Column(length = 250)
    var refreshToken: String? = refreshToken
        protected set

    @Comment(value = "Refresh Token 만료일")
    var tokenExpirationTime: LocalDateTime? = tokenExpirationTime
        protected set

    /**
     * 회원의 Refresh 토큰과 만료 Date 정보를 업데이트합니다.
     * @param refreshToken Refresh 토큰
     * @param expireDate Refresh 토큰 만료일시
     */
    fun updateRefreshToken(refreshToken: String, expireDate: Date) {
        this.refreshToken = refreshToken
        tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(expireDate)
    }

    fun expireRefreshToken(now: LocalDateTime?) {
        tokenExpirationTime = now
    }

    fun changeMemberInfo(username: String, profile: String) {
        this.username = username
        this.profile = profile
    }

}
