package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCodes

class ForbiddenAdminException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.FORBIDDEN_ADMIN,
    properties = "forbidden.admin",
    arguments = arrayOf(),
    cause = cause,
)