package app.askresume.domain.job.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class JobNotFoundException(
    jobId: Long,
    override val cause: Throwable? = null,
) : NewBusinessException(
    ErrorCodes.ENTITY_NOT_FOUND,
    "new.job.not.exists",
    arrayOf(jobId),
    cause,
)