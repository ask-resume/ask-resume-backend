package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class DuplicateMemberException(
    email: String,
    memberType: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ALREADY_REGISTERED_MEMBER,
    properties = "already.registered.member",
    arguments = arrayOf(email, memberType),
    cause = cause,
)

