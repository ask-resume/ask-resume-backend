package app.askresume.domain.member.repository

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByEmailAndMemberType(email: String, memberType: MemberType): Boolean

    fun findByEmailAndMemberType(email: String, memberType: MemberType): Member?

    fun findByRefreshToken(refreshToken: String): Member?

}
