package app.askresume.global.cookie

import org.springframework.http.ResponseCookie

interface CookieProvider {

    fun createCookie(cookieOption: CookieOption): ResponseCookie

}