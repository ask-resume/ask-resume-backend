package app.askresume.global.interceptor

import app.askresume.domain.member.constant.Role
import app.askresume.global.error.exception.ForbiddenAdminException
import app.askresume.global.jwt.service.TokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AdminAuthorizationInterceptor(
    private val tokenManager: TokenManager,
    @Value("\${spring.profiles.active}") var profile: String
) : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.uppercase() == HttpMethod.OPTIONS.name.uppercase()) return true
        if (profile.uppercase() == PROFILE_LOCAL) return true

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val accessToken = authorizationHeader.split(" ")[1]

        val tokenClaims = tokenManager.getTokenClaims(accessToken)
        val role = tokenClaims["role"] as String
        if (Role.ADMIN != Role.valueOf(role)) {
            throw ForbiddenAdminException()
        }

        return true
    }

    companion object {
        private const val PROFILE_LOCAL = "LOCAL"
    }
}
