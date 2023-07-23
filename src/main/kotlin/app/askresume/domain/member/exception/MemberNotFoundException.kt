package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class MemberNotFoundException(
    memberId: Long,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "member.not.exists",
    arguments = arrayOf(memberId),
    cause = cause,
)

