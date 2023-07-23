package app.askresume.global.jwt.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class JwtClaimNotExistsException(
    claimName: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "jwt.claim.not.exists",
    arguments = arrayOf(claimName),
    cause = cause,
)