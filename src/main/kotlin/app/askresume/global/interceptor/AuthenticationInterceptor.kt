package app.askresume.global.interceptor

import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.AuthenticationException
import app.askresume.global.jwt.constant.TokenType
import app.askresume.global.jwt.service.TokenManager
import app.askresume.global.util.AuthorizationHeaderUtils
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationInterceptor(
    private val tokenManager: TokenManager,
) : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // 1. Authorization Header 검증
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader)

        // 2. 토큰 검증
        val token = authorizationHeader.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        tokenManager.validateToken(token)

        // 3. 토큰 타입
        val tokenClaims = tokenManager.getTokenClaims(token)
        val tokenType = tokenClaims.subject
        if (!TokenType.isAccessToken(tokenType)) {
            throw AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE)
        }

        return true
    }

}

