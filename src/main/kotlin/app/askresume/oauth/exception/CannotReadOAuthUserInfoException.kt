package app.askresume.oauth.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class CannotReadOAuthUserInfoException(
    providerName: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "oauth.userinfo.cannot.read",
    arguments = arrayOf(providerName),
    cause = cause,
)