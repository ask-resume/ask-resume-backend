package app.askresume.domain.generative.interview.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class DifficultyNotExistsException(
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENUM_VALIDATE_NOT_EXIST,
    properties = "difficulty.not.exists",
    arguments = arrayOf(),
    cause = cause,
)