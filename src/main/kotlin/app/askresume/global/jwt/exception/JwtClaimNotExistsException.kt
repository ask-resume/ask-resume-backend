package app.askresume.global.jwt.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.NewBusinessException

open class JwtClaimNotExistsException(
    claimName: String,
    override val cause: Throwable? = null,
) : NewBusinessException(
    codeBook = ErrorCodes.ENTITY_NOT_FOUND,
    properties = "jwt.claim.not.exists",
    arguments = arrayOf(claimName),
    cause = cause,
)