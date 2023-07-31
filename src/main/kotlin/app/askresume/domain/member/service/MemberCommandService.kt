package app.askresume.domain.member.service

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.findMemberById
import app.askresume.domain.member.repository.validateDuplicateMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class MemberCommandService(
    private val memberRepository: MemberRepository,
) {

    fun registerMember(
        email: String,
        name: String,
        profile: String?,
        locale: String,
        memberType: MemberType,
    ): Long {
        memberRepository.validateDuplicateMember(email, memberType)

        return memberRepository.save(
            Member(
                email = email,
                memberType = memberType,
                locale = locale,
                role = Role.USER,
                username = name,
                profile = profile,
            )
        ).id !!
    }


    fun secessionMember(memberId: Long) {
        val member = memberRepository.findMemberById(memberId)

        memberRepository.delete(member)
    }

    fun expireRefreshToken(memberId: Long) {
        val member = memberRepository.findMemberById(memberId)

        member.expireRefreshToken(LocalDateTime.now())
    }

    fun modifyMemberInfo(memberId: Long, username: String, profile: String) {
        val member = memberRepository.findMemberById(memberId)

        member.changeMemberInfo(
            username = username,
            profile = profile,
        )
    }

    fun updateRefreshToken(memberId: Long, refreshToken: String, expireDate: Date) {
        val member = memberRepository.findMemberById(memberId)

        member.updateRefreshToken(
            refreshToken = refreshToken,
            expireDate = expireDate,
        )
    }
}