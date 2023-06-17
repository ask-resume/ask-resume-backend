package app.askresume.global.cookie.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

class CookieNotFoundException(
    cookieName: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "cookie.not.exists",
    arguments = arrayOf(cookieName),
    cause = cause,
)