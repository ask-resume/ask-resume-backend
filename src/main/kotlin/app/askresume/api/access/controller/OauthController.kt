package app.askresume.api.access.controller

import app.askresume.api.access.usecase.OauthUseCase
import app.askresume.global.cookie.CookieProvider
import app.askresume.oauth.OAuthProperties
import app.askresume.oauth.constant.OAuthProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.servlet.http.HttpServletResponse

@Tag(name = "oauth2", description = "OAuth2.0 API")
@RestController
@RequestMapping("/api/oauth")
class OauthController(
    private val oauthUseCase: OauthUseCase,
    private val cookieProvider: CookieProvider,
    private val oauthProperties: OAuthProperties,
) {

    @Tag(name = "oauth2")
    @Operation(
        summary = "OAuth2.0 로그인 API",
        description = """
            🛑 절대 이 API를 비동기 (axios, ajax, fetch) 로 호출하지 마세요!
            반드시 해당 URL을 '링크'로 걸어 클라이언트가 아닌 서버측에서 OAuth 로그인을 진행해야 구글의 CORS 보안정책을 통과할 수 있습니다.
            
            벤더사(provider)의 로그인 페이지로 리다이렉트된 후 OAuth2.0 로그인이 진행됩니다.
            (302 Redirect) 만약 로그인에 성공하면 백엔드에 설정된 클라이언트 페이지로 리다이렉트됩니다.
            """
    )
    @GetMapping("/{provider}/login")
    fun login(@PathVariable provider: String, response: HttpServletResponse): ResponseEntity<String> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val redirectUri = oauthUseCase.getAuthorizationUri(providerEnum)

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
        response: HttpServletResponse,
    ): ResponseEntity<Void> {
        val providerEnum = OAuthProvider.fromKebabCase(provider)
        val jwtTokenSet = oauthUseCase.joinOrLogin(code, providerEnum)

        val jwtCookies = arrayOf(
            cookieProvider.createTokenCookie(jwtTokenSet.accessToken, oauthProperties.domain),
            cookieProvider.createTokenCookie(jwtTokenSet.refreshToken, oauthProperties.domain)
        )

        val headers = HttpHeaders()
        jwtCookies.forEach { jwtCookie ->
            headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        }

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI(oauthProperties.clientHome))
            .headers(headers)
            .build()
    }
}
