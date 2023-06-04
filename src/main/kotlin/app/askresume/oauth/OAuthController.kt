package app.askresume.oauth

import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.service.OAuthCookieProvider
import app.askresume.oauth.service.OAuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val oAuthService: OAuthService,
    private val oAuthCookieProvider: OAuthCookieProvider,
) {

    @GetMapping("/{provider}/login")
    @ResponseBody
    fun login(@PathVariable provider: String): ResponseEntity<String> {
        val providerEnum = OAuthProvider.valueOf(provider)
        val redirectUri = oAuthService.authorize(providerEnum)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(redirectUri)
            .build()
    }

    @GetMapping("/{provider}/callback")
    fun loginCallback(@PathVariable provider: String, code: String): ResponseEntity<Void> {
        if (OAuthProvider.values().none { it.name == provider })
            throw Exception("해당 Provider를 통한 로그인은 제공되지 않습니다. : $provider")

        val providerEnum = OAuthProvider.valueOf(provider)

        val oAuthTokenDTO = oAuthService.getToken(code, providerEnum)
        val accessTokenCookie = oAuthCookieProvider.generateAccessTokenCookie(oAuthTokenDTO)
        val refreshTokenCookie = oAuthCookieProvider.generateRefreshTokenCookie(oAuthTokenDTO)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI("http://localhost:8080/login"))
            .headers(headers)
            .build()
    }

}
