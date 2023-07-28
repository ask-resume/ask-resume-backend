package app.askresume.domain.member.repository

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.exception.MemberNotFoundException
import app.askresume.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.findMemberById(id: Long) : Member =
    findByIdOrNull(id) ?: throw MemberNotFoundException(id)

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByEmailAndMemberType(email: String, memberType: MemberType): Boolean

    fun findByEmailAndMemberType(email: String, memberType: MemberType): Member?

    fun findByRefreshToken(refreshToken: String): Member?

}
