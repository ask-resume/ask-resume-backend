package app.askresume.api.access.controller;

import app.askresume.api.access.dto.request.LoginRequest;
import app.askresume.api.access.dto.response.AccessTokenResponse;
import app.askresume.api.access.dto.response.LoginResponse;
import app.askresume.api.access.facade.AccessFacade;
import app.askresume.api.access.validator.OauthValidator;
import app.askresume.domain.member.constant.MemberType;
import app.askresume.global.model.ApiResult;
import app.askresume.global.resolver.token.AuthorizationToken;
import app.askresume.global.resolver.token.TokenDto;
import app.askresume.api.access.dto.request.OauthLoginRequest;
import app.askresume.api.access.dto.request.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccessController {

    private final OauthValidator oauthValidator;

    private final AccessFacade accessFacade;

    @Tag(name = "authentication")
    @Operation(summary = "일반 로그인 API", description = "일반 로그인 API")
    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {


        return ResponseEntity.ok(new ApiResult<>(accessFacade.login(loginRequest)));
    }

    @Tag(name = "authentication")
    @Operation(summary = "일반 회원가입 API", description = "일반 회원가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@Validated @RequestBody SignUpRequest signUpRequest) {
        accessFacade.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Tag(name = "authentication")
    @Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
    @PostMapping("/oauth/login")
    public ResponseEntity<ApiResult<LoginResponse>> oauthLogin(@RequestBody OauthLoginRequest oauthLoginRequestDto,
                                                               @AuthorizationToken TokenDto token) {

        oauthValidator.validateMemberType(oauthLoginRequestDto.memberType());
        MemberType memberType = MemberType.from(oauthLoginRequestDto.memberType());

        return ResponseEntity.ok(new ApiResult<>(accessFacade.oauthLogin(token.getToken(), memberType)));
    }

    @Tag(name = "authentication")
    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthorizationToken TokenDto token) {
        accessFacade.logout(token.getToken());

        return ResponseEntity.noContent().build();
    }

    @Tag(name = "authentication")
    @Operation(summary = "Access Token 재발급 API", description = "Access Token 재발급 API")
    @PostMapping("/reissued/access-token")
    public ResponseEntity<ApiResult<AccessTokenResponse>> createAccessToken(@AuthorizationToken TokenDto token) {
        return ResponseEntity.ok(new ApiResult<>(accessFacade.createAccessTokenByRefreshToken(token.getToken())));
    }
}
