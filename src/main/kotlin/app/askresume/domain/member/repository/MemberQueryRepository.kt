package app.askresume.domain.member.repository

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.dto.MemberInfoDto
import app.askresume.domain.member.dto.QMemberInfoDto
import app.askresume.domain.member.model.QMember.member
import app.askresume.domain.member.repository.expression.MemberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun findQueryMemberInfo(
        memberId: Long? = null,
        email: String? = null,
        memberType: MemberType? = null,
        refreshToken: String? = null,
    ): MemberInfoDto? {
        return queryFactory
            .select(
                QMemberInfoDto(
                    member.id,
                    member.email,
                    member.memberType,
                    member.locale,
                    member.role,
                    member.username,
                    member.profile,
                    member.refreshToken,
                    member.tokenExpirationTime,
                )
            )
            .from(member)
            .where(
                MemberExpression.memberIdEq(memberId),
                MemberExpression.emailEq(email),
                MemberExpression.memberTypeEq(memberType),
                MemberExpression.refreshTokenEq(refreshToken)
            )
            .fetchOne()
    }

}