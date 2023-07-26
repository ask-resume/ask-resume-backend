package app.askresume.domain.submit.repository.expression

import app.askresume.domain.submit.model.QSubmit.submit

object SubmitExpression {

    fun memberIdEq(memberId : Long?) = memberId?.let { submit.member.id.eq(memberId) }

}