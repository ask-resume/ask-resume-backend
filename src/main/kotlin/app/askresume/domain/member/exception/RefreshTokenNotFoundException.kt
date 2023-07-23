package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class RefreshTokenNotFoundException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.REFRESH_TOKEN_NOT_FOUND,
    properties = "refresh.token.not.found",
    arguments = arrayOf(),
    cause = cause,
)

