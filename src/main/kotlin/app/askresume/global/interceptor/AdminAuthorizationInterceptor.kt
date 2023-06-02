package app.askresume.global.interceptor

import app.askresume.domain.member.constant.Role
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.AuthenticationException
import app.askresume.global.jwt.service.TokenManager
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AdminAuthorizationInterceptor(
    private val tokenManager: TokenManager,
) : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val accessToken = authorizationHeader.split(" ")[1]

        val tokenClaims = tokenManager.getTokenClaims(accessToken)
        val role = tokenClaims["role"] as String
        if (Role.ADMIN != Role.valueOf(role)) {
            throw AuthenticationException(ErrorCode.FORBIDDEN_ADMIN)
        }

        return true
    }
}
