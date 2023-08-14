package app.askresume.global.config.jpa

import app.askresume.global.jwt.service.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.AuditorAware
import org.springframework.http.HttpHeaders
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuditorAwareImpl : AuditorAware<Long> {

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

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
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return authorizationHeader.split(" ")[1]
    }

    companion object {
        private const val PROFILE_LOCAL = "LOCAL"
    }

}

