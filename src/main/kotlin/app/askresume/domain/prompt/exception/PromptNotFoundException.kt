package app.askresume.domain.prompt.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class PromptNotFoundException(
    promptType: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "prompt.not.exists",
    arguments = arrayOf(promptType),
    cause = cause,
)