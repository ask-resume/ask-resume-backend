package app.askresume.global.config.jpa

import app.askresume.global.cookie.CookieProvider
import app.askresume.global.error.exception.NotAccessTokenTypeException
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.service.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.AuditorAware
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuditorAwareImpl : AuditorAware<Long> {

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

    @Autowired
    private lateinit var cookieProvider: CookieProvider

    @Value("\${spring.profiles.active}")
    private lateinit var profile: String

    override fun getCurrentAuditor(): Optional<Long> {
        if (profile.uppercase() == PROFILE_LOCAL) return Optional.of(1L)

        val jwtToken = extractJwtTokenFromRequest(httpServletRequest)

        if (jwtToken != null) {
            val memberId = tokenManager.getMemberIdFromAccessToken(jwtToken)
            return Optional.of(memberId)
        } else {
            throw RuntimeException("에러")
        }
    }

    private fun extractJwtTokenFromRequest(request: HttpServletRequest): String? {

        val accessTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.ACCESS.cookieName)
            ?: throw NotAccessTokenTypeException()
        return accessTokenCookie.value
    }

    companion object {
        private const val PROFILE_LOCAL = "LOCAL"
    }

}

