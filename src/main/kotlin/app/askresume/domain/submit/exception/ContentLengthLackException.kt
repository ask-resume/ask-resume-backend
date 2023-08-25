package app.askresume.domain.submit.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException


open class ContentLengthLackException(
    contentSize : Int,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.INVALID_CONTENT,
    properties = "content.length.lack",
    arguments = arrayOf(contentSize),
    cause = cause,
)