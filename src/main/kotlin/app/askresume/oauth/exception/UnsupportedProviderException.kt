package app.askresume.oauth.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class UnsupportedProviderException(
    providerName: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "oauth.provider.unsupported",
    arguments = arrayOf(providerName),
    cause = cause,
)