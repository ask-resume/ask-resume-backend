package app.askresume.api.access.controller

import app.askresume.api.access.dto.request.LoginRequest
import app.askresume.api.access.dto.request.OauthLoginRequest
import app.askresume.api.access.dto.request.SignUpRequest
import app.askresume.api.access.dto.response.AccessTokenResponse
import app.askresume.api.access.dto.response.LoginResponse
import app.askresume.api.access.facade.AccessFacade
import app.askresume.api.member.validator.OauthValidator
import app.askresume.domain.member.constant.MemberType
import app.askresume.global.model.ApiResult
import app.askresume.global.resolver.token.TokenDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
@RestController
@RequestMapping("/api")
class AccessController(
    private val oauthValidator: OauthValidator,
    private val accessFacade: AccessFacade,
) {

    @Tag(name = "authentication")
    @Operation(summary = "일반 로그인 API", description = "일반 로그인 API")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResult<LoginResponse>> {
        return ResponseEntity.ok(ApiResult(accessFacade.login(loginRequest)))
    }

    @Tag(name = "authentication")
    @Operation(summary = "일반 회원가입 API", description = "일반 회원가입 API")
    @PostMapping("/sign-up")
    fun register(@Validated @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Void> {
        accessFacade.register(signUpRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Tag(name = "authentication")
    @Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
    @PostMapping("/oauth/login")
    fun oauthLogin(
        @RequestBody oauthLoginRequestDto: OauthLoginRequest,
        token: TokenDto,
    ): ResponseEntity<ApiResult<LoginResponse>> {

        oauthValidator.validateMemberType(oauthLoginRequestDto.memberType)
        val memberType: MemberType = MemberType.from(oauthLoginRequestDto.memberType)

        return ResponseEntity.ok(ApiResult(accessFacade.oauthLogin(token.token, memberType)))
    }

    @Tag(name = "authentication")
    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    fun logout(token: TokenDto): ResponseEntity<Void> {
        accessFacade.logout(token.token)

        return ResponseEntity.noContent().build()
    }

    @Tag(name = "authentication")
    @Operation(summary = "Access Token 재발급 API", description = "Access Token 재발급 API")
    @PostMapping("/reissued/access-token")
    fun createAccessToken(token: TokenDto): ResponseEntity<ApiResult<AccessTokenResponse>> {
        return ResponseEntity.ok(ApiResult(accessFacade.createAccessTokenByRefreshToken(token.token)))
    }
}

