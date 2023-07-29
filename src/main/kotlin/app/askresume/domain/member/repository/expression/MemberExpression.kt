package app.askresume.domain.member.repository.expression

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.model.QMember.member


object MemberExpression {

    fun memberIdEq(memberId: Long?) = memberId?.let { member.id.eq(memberId) }

    fun emailEq(email: String?) = email?.let { member.email.eq(email) }

    fun memberTypeEq(memberType: MemberType?) = memberType?.let { member.memberType.eq(memberType) }

}