package app.askresume.global.cookie

import app.askresume.global.jwt.dto.JwtResponse
import org.springframework.http.ResponseCookie
import javax.servlet.http.Cookie

interface CookieProvider {

    /**
     * 새 쿠키를 만듭니다.
     */
    fun createCookie(cookieOption: CookieOption): ResponseCookie

    /**
     * JWT 토큰 정보로 쿠키를 만듭니다.
     * @return JWT 토큰 Cookie
     * @param tokenDto JWT 토큰 정보가 담긴 DTO
     * @param domain 쿠키가 발급될 도메인 정보
     */
    fun createTokenCookie(tokenDto: JwtResponse.Token, domain: String): ResponseCookie

    /**
     * 쿠키 목록에서 특정 쿠키를 가져옵니다.
     * @return Cookie (찾는 쿠키가 없으면 null)
     * @param cookies 쿠키 목록 정보
     * @param cookieName 가져올 쿠키의 이름
     */
    fun getCookie(cookies: Array<out Cookie>, cookieName: String): Cookie?

}