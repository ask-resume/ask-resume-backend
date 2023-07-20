package app.askresume.domain.extract.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class NotPermittedContentTypeException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "not.permitted.content.type",
    arguments = arrayOf(),
    cause = cause,
)