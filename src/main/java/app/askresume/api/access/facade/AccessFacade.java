package app.askresume.api.access.facade;

import app.askresume.api.access.dto.request.LoginRequest;
import app.askresume.api.access.dto.request.SignUpRequest;
import app.askresume.api.access.dto.response.AccessTokenResponse;
import app.askresume.api.access.dto.response.LoginResponse;
import app.askresume.api.access.validator.PasswordValidator;
import app.askresume.domain.member.constant.MemberType;
import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;
import app.askresume.domain.member.service.MemberService;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.AuthenticationException;
import app.askresume.global.jwt.constant.GrantType;
import app.askresume.global.jwt.constant.TokenType;
import app.askresume.global.jwt.dto.JwtTokenDto;
import app.askresume.global.jwt.service.TokenManager;
import app.askresume.global.util.SHA256Util;
import app.askresume.oauth.model.OAuthAttributes;
import app.askresume.oauth.service.SocialLoginApiService;
import app.askresume.oauth.service.SocialLoginApiServiceFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessFacade {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    @Transactional
    public void register(SignUpRequest signUpRequest) {
        PasswordValidator.passwordCheck(signUpRequest.password(), signUpRequest.passwordCheck());

        Member member = signUpRequest.toMemberEntity(MemberType.LOCAL, Role.USER);
        memberService.registerMember(member);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {

        final String encryptPassword = SHA256Util.encrypt(loginRequest.password());
        loginRequest = new LoginRequest(loginRequest.email(), encryptPassword);


        final String email = loginRequest.email();
        final String password = loginRequest.password();
        final MemberType memberType = MemberType.LOCAL;

        Member member = memberService.findMemberByEmailAndPasswordAndMemberType(email, password, memberType);
        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(member.getId(), member.getRole());
        member.updateRefreshToken(jwtTokenDto);

        return LoginResponse.of(jwtTokenDto);
    }

    @Transactional
    public LoginResponse oauthLogin(String accessToken, MemberType memberType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.debug(userInfo.toString());

        JwtTokenDto jwtTokenDto;
        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.email(), memberType);

        Member oauthMember;
        if (optionalMember.isEmpty()) { // 신규 회원 가입
            oauthMember = userInfo.toMemberEntity(memberType, Role.USER);
            oauthMember = memberService.registerMember(oauthMember);

        } else { // 기존 회원일 경우
            oauthMember = optionalMember.get();
        }
        // 토큰 생성
        jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getId(), oauthMember.getRole());
        oauthMember.updateRefreshToken(jwtTokenDto);

        return LoginResponse.of(jwtTokenDto);
    }

    public void logout(String accessToken) {

        // 1. 토큰 검증
        tokenManager.validateToken(accessToken);

        // 2. 토큰 타입 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();

        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 3. refresh token 만료 처리
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        Member member = memberService.findMemberById(memberId);
        member.expireRefreshToken(LocalDateTime.now());
    }

    public AccessTokenResponse createAccessTokenByRefreshToken(String refreshToken) {
        Member member = memberService.findMemberByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(member.getId(), member.getRole(), accessTokenExpireTime);

        return AccessTokenResponse.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
