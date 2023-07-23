package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCodes

class NotValidTokenException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.NOT_VALID_TOKEN,
    properties = "not.valid.token",
    arguments = arrayOf(),
    cause = cause,
)