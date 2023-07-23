package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class RefreshTokenExpiredException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.REFRESH_TOKEN_EXPIRED,
    properties = "refresh.token.expired",
    arguments = arrayOf(),
    cause = cause,
)

