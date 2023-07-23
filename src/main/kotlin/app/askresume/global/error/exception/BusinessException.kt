package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCodes

open class BusinessException(
    open val codeBook: ErrorCodes,
    val properties: String,
    val arguments: Array<Any>,
    override val cause: Throwable? = null,
) : RuntimeException(codeBook.description, cause)