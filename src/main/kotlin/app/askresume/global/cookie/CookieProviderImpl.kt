package app.askresume.global.cookie

import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service

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

}