package app.askresume.api.access.controller

import app.askresume.api.access.facade.OAuthFacade
import app.askresume.global.cookie.CookieOption
import app.askresume.global.cookie.CookieProvider
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.service.OAuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Tag(name = "oauth", description = "OAuth2.0 API")
@RestController
@RequestMapping("/api/oauth")
class OAuthController(
    private val oAuthService: OAuthService,
    private val oAuthFacade: OAuthFacade,
    private val cookieProvider: CookieProvider,
    private val oAuthProperties: OAuthProperties,
) {

    @GetMapping("/{provider}/login")
    fun login(@PathVariable provider: String, response: HttpServletResponse): ResponseEntity<String> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val redirectUri = oAuthService.authorize(providerEnum)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(redirectUri)
            .build()
    }

    @GetMapping("/{provider}/callback")
    fun loginCallback(
        @PathVariable provider: String,
        code: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Void> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val jwtDto = oAuthFacade.joinOrLogin(code, providerEnum)

        val headers = HttpHeaders()
        val domain = request.serverName
        headers.add(HttpHeaders.SET_COOKIE, getAccessTokenCookie(jwtDto, domain))
        headers.add(HttpHeaders.SET_COOKIE, getRefreshTokenCookie(jwtDto, domain))

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI(oAuthProperties.clientHome))
            .headers(headers)
            .build()
    }

    private fun getAccessTokenCookie(jwtTokenDto: JwtResponse.TokenDto, domain: String): String {
        val cookieOption = CookieOption(
            key = JwtTokenType.ACCESS.cookieName,
            value = jwtTokenDto.accessToken,
            domain = domain,
            maxAge = Duration.ofMillis(jwtTokenDto.accessTokenExpirationTime),
            httpOnly = true,
            secure = true,
        )
        return cookieProvider.createCookie(cookieOption).toString()
    }

    private fun getRefreshTokenCookie(jwtTokenDto: JwtResponse.TokenDto, domain: String): String {
        val cookieOption = CookieOption(
            key = JwtTokenType.REFRESH.cookieName,
            value = jwtTokenDto.refreshToken,
            domain = domain,
            maxAge = Duration.ofMillis(jwtTokenDto.refreshTokenExpirationTime),
            httpOnly = true,
            secure = true,
        )
        return cookieProvider.createCookie(cookieOption).toString()
    }

}
