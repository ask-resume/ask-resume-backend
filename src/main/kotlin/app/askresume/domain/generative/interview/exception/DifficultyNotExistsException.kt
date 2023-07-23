package app.askresume.domain.generative.interview.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class DifficultyNotExistsException(
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENUM_VALIDATE_NOT_EXIST,
    properties = "difficulty.not.exists",
    arguments = arrayOf(),
    cause = cause,
)