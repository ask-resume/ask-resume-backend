package app.askresume.domain.submit.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class SubmitDataNotFoundException(
    submitId: Long,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "submit.data.not.exists",
    arguments = arrayOf(submitId),
    cause = cause,
)