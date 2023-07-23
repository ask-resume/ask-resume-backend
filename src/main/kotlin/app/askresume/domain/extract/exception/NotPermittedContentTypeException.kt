package app.askresume.domain.extract.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class NotPermittedContentTypeException(
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "not.permitted.content.type",
    arguments = arrayOf(),
    cause = cause,
)