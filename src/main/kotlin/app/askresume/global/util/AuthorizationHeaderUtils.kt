package app.askresume.global.util

import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.AuthenticationException
import app.askresume.global.jwt.constant.JwtGrantType
import org.springframework.util.StringUtils

/**
 * Header에서 Authorization 관련 체크 Util
 *
 * @author igor
 */
object AuthorizationHeaderUtils {
    fun validateAuthorization(authorizationHeader: String) {

        // 1. authorizationHeader 필수 체크
        if (!StringUtils.hasText(authorizationHeader)) {
            throw AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION)
        }

        // 2. authorizationHeader Bearer 체크
        val authorizations = authorizationHeader.split(" ")
        if (authorizations.size < 2 || JwtGrantType.BEARER.type != authorizations[0]) {
            throw AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE)
        }
    }
}

