package app.askresume.global.cookie

import app.askresume.global.jwt.dto.JwtResponse
import org.springframework.core.env.Environment
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import java.time.Duration
import javax.servlet.http.Cookie

@Service
class CookieProviderImpl(
    private val environment: Environment,
) : CookieProvider {

    override fun createCookie(cookieOption: CookieOption): ResponseCookie {
        val (key, value, httpOnly, secure, domain, path, maxAge) = cookieOption

        val cookie = ResponseCookie.from(key, value)
            .httpOnly(httpOnly)
            .secure(secure)
            .domain(domain)
            .path(path)

        maxAge?.let { cookie.maxAge(maxAge) }

        return cookie.build()
    }

    override fun createTokenCookie(tokenDto: JwtResponse.Token, domain: String): ResponseCookie {
        val isProdActive = environment.activeProfiles.any { it == "prod" }

        val cookieOption = CookieOption(
            name = tokenDto.tokenType.cookieName,
            value = tokenDto.token,
            domain = domain,
            maxAge = Duration.ofMillis(tokenDto.expirationTime),
            httpOnly = true,
            secure = isProdActive,
        )

        return createCookie(cookieOption)
    }

    override fun getCookie(cookies: Array<out Cookie>, cookieName: String): Cookie? {
        return cookies.let {
            cookies.find { it.name == cookieName }
        }
    }

}