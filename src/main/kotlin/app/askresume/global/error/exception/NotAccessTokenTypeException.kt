package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCodes

open class NotAccessTokenTypeException (
    override val cause: Throwable? = null,
    ) : BusinessException(
    codeBook = ErrorCodes.NOT_ACCESS_TOKEN_TYPE,
    properties = "not.access.token.type",
    arguments = arrayOf(),
    cause = cause,
    )

