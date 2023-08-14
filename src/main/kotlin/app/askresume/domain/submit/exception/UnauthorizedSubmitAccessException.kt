package app.askresume.domain.submit.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class UnauthorizedSubmitAccessException(
    submitId: Long,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.UNAUTHORIZED_ACCESS,
    properties = "unauthorized.submit.access",
    arguments = arrayOf(submitId),
    cause = cause,
)