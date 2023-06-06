package app.askresume.global.cookie

import app.askresume.global.jwt.constant.JwtTokenType
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import javax.servlet.http.Cookie

@Service
class CookieProviderImpl : CookieProvider {

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

    override fun getCookie(cookies: Array<out Cookie>, cookieName: String): Cookie? {
        return cookies.let {
            cookies.find { it.name == cookieName }
        }
    }

}