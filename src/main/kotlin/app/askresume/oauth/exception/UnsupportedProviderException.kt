package app.askresume.oauth.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class UnsupportedProviderException(
    providerName: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "oauth.provider.unsupported",
    arguments = arrayOf(providerName),
    cause = cause,
)