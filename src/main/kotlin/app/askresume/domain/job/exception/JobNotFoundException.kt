package app.askresume.domain.job.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class JobNotFoundException(
    jobId: Long,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "new.job.not.exists",
    arguments = arrayOf(jobId),
    cause = cause,
)