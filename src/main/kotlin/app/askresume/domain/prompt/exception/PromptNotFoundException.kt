package app.askresume.domain.prompt.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class PromptNotFoundException(
    promptType: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "prompt.not.exists",
    arguments = arrayOf(promptType),
    cause = cause,
)