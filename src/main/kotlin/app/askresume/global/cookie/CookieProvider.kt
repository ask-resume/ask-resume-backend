package app.askresume.global.cookie

import org.springframework.http.ResponseCookie
import javax.servlet.http.Cookie

interface CookieProvider {

    fun createCookie(cookieOption: CookieOption): ResponseCookie

    /**
     * 쿠키 목록에서 특정 쿠키를 가져옵니다.
     * @return Cookie (찾는 쿠키가 없으면 null)
     * @param cookies 쿠키 목록 정보
     * @param cookieName 가져올 쿠키의 이름
     */
    fun getCookie(cookies: Array<out Cookie>, cookieName: String): Cookie?

}