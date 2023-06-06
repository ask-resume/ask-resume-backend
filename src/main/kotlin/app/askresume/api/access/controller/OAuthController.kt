package app.askresume.api.access.controller

import app.askresume.api.access.facade.OAuthFacade
import app.askresume.global.cookie.CookieOption
import app.askresume.global.cookie.CookieProvider
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.dto.JwtResponse
import app.askresume.global.resolver.token.RefreshToken
import app.askresume.global.resolver.token.TokenDto
import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.constant.OAuthProvider
import app.askresume.oauth.service.OAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Tag(name = "oauth2", description = "OAuth2.0 API")
@RestController
@RequestMapping("/api/oauth")
class OAuthController(
    private val oAuthService: OAuthService,
    private val oAuthFacade: OAuthFacade,
    private val cookieProvider: CookieProvider,
    private val oAuthProperties: OAuthProperties,
) {

    @Tag(name = "oauth2")
    @Operation(
        summary = "OAuth2.0 로그인 API",
        description = "클라이언트에서 이 API를 호출하면 벤더사(provider)의 로그인 페이지로 리다이렉트된 후 OAuth2.0 로그인이 진행됩니다. (302 Redirect) 만약 로그인에 성공하면 백엔드에 설정된 클라이언트 페이지로 리다이렉트됩니다."
    )
    @GetMapping("/{provider}/login")
    fun login(@PathVariable provider: String, response: HttpServletResponse): ResponseEntity<String> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val redirectUri = oAuthService.authorize(providerEnum)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(redirectUri)
            .build()
    }

    @Tag(name = "oauth2")
    @Operation(
        summary = "OAuth2.0 로그인 콜백 API",
        description = "클라이언트에서 OAuth2.0 로그인 동의를 완료하면 Google, Linked In 등의 벤더사(provider)에서 이 API를 호출해 access token을 발급받을 수 있는 code를 백엔드로 넘겨줍니다."
    )
    @GetMapping("/{provider}/callback")
    fun loginCallback(
        @PathVariable provider: String,
        code: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Void> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val jwtTokenSet = oAuthFacade.joinOrLogin(code, providerEnum)

        val headers = HttpHeaders()
        val domain = request.serverName
        headers.add(HttpHeaders.SET_COOKIE, getTokenCookie(JwtTokenType.ACCESS, jwtTokenSet.accessToken, domain))
        headers.add(HttpHeaders.SET_COOKIE, getTokenCookie(JwtTokenType.REFRESH, jwtTokenSet.refreshToken, domain))

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI(oAuthProperties.clientHome))
            .headers(headers)
            .build()
    }

    @Tag(name = "oauth2")
    @Operation(summary = "Access Token 재발급 API", description = "쿠키에 저장된 Refresh 토큰을 읽어와 만료된 Access 토큰을 재발급해줍니다.")
    @GetMapping("/refresh")
    fun createAccessToken(
        @RefreshToken token: TokenDto,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        val accessTokenDto = oAuthFacade.createAccessTokenByRefreshToken(token.token)

        val headers = HttpHeaders()
        val domain = request.serverName
        headers.add(HttpHeaders.SET_COOKIE, getTokenCookie(JwtTokenType.ACCESS, accessTokenDto, domain))

        return ResponseEntity
            .ok()
            .headers(headers)
            .build()
    }

    private fun getTokenCookie(tokenType: JwtTokenType, tokenDto: JwtResponse.Token, domain: String): String {
        val cookieOption = CookieOption(
            key = tokenType.cookieName,
            value = tokenDto.token,
            domain = domain,
            maxAge = Duration.ofMillis(tokenDto.expirationTime),
            httpOnly = true,
            secure = true,
        )
        return cookieProvider.createCookie(cookieOption).toString()
    }

}
