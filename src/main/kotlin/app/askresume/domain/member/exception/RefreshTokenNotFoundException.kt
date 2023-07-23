package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class RefreshTokenNotFoundException(
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.REFRESH_TOKEN_NOT_FOUND,
    properties = "refresh.token.not.found",
    arguments = arrayOf(),
    cause = cause,
)

