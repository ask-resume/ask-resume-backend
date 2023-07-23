package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCodes


class TokenExpiredException (
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.TOKEN_EXPIRED,
    properties = "token.expired",
    arguments = arrayOf(),
    cause = cause,
)