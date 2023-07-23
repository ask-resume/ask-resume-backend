package app.askresume.oauth.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class CannotReadOAuthUserInfoException(
    providerName: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "oauth.userinfo.cannot.read",
    arguments = arrayOf(providerName),
    cause = cause,
)