package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class RefreshTokenExpiredException(
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.REFRESH_TOKEN_EXPIRED,
    properties = "refresh.token.expired",
    arguments = arrayOf(),
    cause = cause,
)

