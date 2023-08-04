package app.askresume.global.interceptor

import app.askresume.global.cookie.CookieProvider
import app.askresume.global.error.exception.NotAccessTokenTypeException
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.service.TokenManager
import org.springframework.http.HttpMethod
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationInterceptor(
    private val tokenManager: TokenManager,
    private val cookieProvider: CookieProvider,
    @Value("\${spring.profiles.active}") var profile: String
) : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.uppercase() != HttpMethod.OPTIONS.name.uppercase()) return true

        return if (profile.uppercase() == PROFILE_LOCAL) {
            true
        } else {
            // 1. Authorization Header 검증
            val accessTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.ACCESS.cookieName)
                ?: throw NotAccessTokenTypeException()

        // 2. 토큰 검증
        val accessToken = accessTokenCookie.value
        tokenManager.validateToken(accessToken)

            // 3. 토큰 타입
            val tokenClaims = tokenManager.getTokenClaims(accessToken)
            val tokenType = tokenClaims.subject
            if (! JwtTokenType.isAccessToken(tokenType)) {
                throw NotAccessTokenTypeException()
            }

            true
        }

    }

    companion object {
        private const val PROFILE_LOCAL = "LOCAL"
    }

}

