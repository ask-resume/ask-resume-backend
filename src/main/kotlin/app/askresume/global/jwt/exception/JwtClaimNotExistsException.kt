package app.askresume.global.jwt.exception

import app.askresume.global.error.ErrorCodes
import app.askresume.global.error.exception.BusinessException

open class JwtClaimNotExistsException(
    claimName: String,
    override val cause: Throwable? = null,
) : BusinessException(
    codeBook = ErrorCodes.NOT_VALID_TOKEN,
    properties = "jwt.claim.not.exists",
    arguments = arrayOf(claimName),
    cause = cause,
)