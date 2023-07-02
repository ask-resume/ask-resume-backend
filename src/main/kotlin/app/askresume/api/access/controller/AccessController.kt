package app.askresume.api.access.controller

import app.askresume.api.access.dto.request.LoginRequest
import app.askresume.api.access.dto.request.SignUpRequest
import app.askresume.api.access.facade.AccessFacade
import app.askresume.global.cookie.CookieOption
import app.askresume.global.cookie.CookieProvider
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.model.ApiResult
import app.askresume.global.resolver.token.AccessToken
import app.askresume.global.resolver.token.RefreshToken
import app.askresume.global.resolver.token.TokenDto
import app.askresume.oauth.OAuthProperties
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Duration
import javax.servlet.http.HttpServletRequest


@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
@RestController
@RequestMapping("/api")
class AccessController(
    private val accessFacade: AccessFacade,
    private val cookieProvider: CookieProvider,
    private val oAuthProperties: OAuthProperties,
) {

    @Tag(name = "authentication")
    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    fun logout(@AccessToken token: TokenDto, request: HttpServletRequest): ResponseEntity<Void> {
        accessFacade.logout(token.token)

        val expiredCookies = getAccessAndRefreshTokenCookies(oAuthProperties.domain, Duration.ZERO)

        val headers = HttpHeaders()
        expiredCookies.forEach { headers.add(HttpHeaders.SET_COOKIE, it.toString()) }

        return ResponseEntity.noContent().headers(headers).build()
    }

    private fun getAccessAndRefreshTokenCookies(domain: String, maxAge: Duration): Array<ResponseCookie> {
        val expiredAccessTokenCookie = cookieProvider.createCookie(
            CookieOption(name = JwtTokenType.ACCESS.cookieName, domain = domain, maxAge = maxAge))

        val expiredRefreshTokenCookie = cookieProvider.createCookie(
            CookieOption(name = JwtTokenType.REFRESH.cookieName, domain = domain, maxAge = maxAge))

        return arrayOf(expiredAccessTokenCookie, expiredRefreshTokenCookie)

    }

    @Tag(name = "authentication")
    @Operation(summary = "Access Token 재발급 API", description = "쿠키에 저장된 Refresh 토큰을 읽어와 만료된 Access 토큰을 재발급해줍니다.")
    @GetMapping("/refresh")
    fun createAccessToken(@RefreshToken token: TokenDto): ResponseEntity<Void> {
        val accessTokenDto = accessFacade.createAccessTokenByRefreshToken(token.token)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.SET_COOKIE, cookieProvider.createTokenCookie(accessTokenDto, oAuthProperties.domain).toString())

        return ResponseEntity
            .ok()
            .headers(headers)
            .build()
    }

}

