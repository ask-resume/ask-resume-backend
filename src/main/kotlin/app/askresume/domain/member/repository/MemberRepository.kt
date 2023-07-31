package app.askresume.domain.member.repository

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.exception.DuplicateMemberException
import app.askresume.domain.member.exception.MemberNotFoundException
import app.askresume.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.findMemberById(id: Long) =
    findByIdOrNull(id) ?: throw MemberNotFoundException(id)

fun MemberRepository.validateDuplicateMember(email: String, memberType: MemberType): Boolean =
    if (existsByEmailAndMemberType(email, memberType)) throw DuplicateMemberException(email, memberType.name)
    else false

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByEmailAndMemberType(email: String, memberType: MemberType): Boolean

}
