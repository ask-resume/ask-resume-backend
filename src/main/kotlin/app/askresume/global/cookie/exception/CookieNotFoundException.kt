package app.askresume.global.cookie.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class CookieNotFoundException(
    cookieName: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "cookie.not.found",
    arguments = arrayOf(cookieName),
    cause = cause,
)