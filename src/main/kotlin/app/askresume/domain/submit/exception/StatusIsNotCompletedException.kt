package app.askresume.domain.submit.exception

import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class SubmitStatusIsNotCompletedException(
    status: SubmitStatus,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.STATUS_IS_NOT_COMPLETED,
    properties = "submit.is.not.completed",
    arguments = arrayOf(status),
    cause = cause,
)