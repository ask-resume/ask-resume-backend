package app.askresume.oauth.google.service;

import app.askresume.domain.member.constant.MemberType;
import app.askresume.global.jwt.constant.GrantType;
import app.askresume.oauth.google.client.GoogleUserInfoClient;
import app.askresume.oauth.google.dto.GoogleUserInfoResponse;
import app.askresume.oauth.model.OAuthAttributes;
import app.askresume.oauth.service.SocialLoginApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginApiServiceImpl implements SocialLoginApiService {
    private final GoogleUserInfoClient googleUserInfoClient;

    @Override
    public OAuthAttributes getUserInfo(final String accessToken) {
        final GoogleUserInfoResponse response = googleUserInfoClient.getGoogleUserInfo(GrantType.BEARER.getType() + " " + accessToken);

        return new OAuthAttributes(response.email(), response.name(), response.picture(), response.locale(), MemberType.GOOGLE);
    }

}
