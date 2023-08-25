package app.askresume.domain.submit.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException


open class ContentLengthOverException(
    contentSize : Int,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "content.length.over",
    arguments = arrayOf(contentSize),
    cause = cause,
)