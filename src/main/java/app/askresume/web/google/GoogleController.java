package app.askresume.web.google;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleTokenClient googleTokenClient;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @GetMapping("/oauth/google-callback")
    public @ResponseBody String loginCallback(String code) {
        final String grantType = "authorization_code";
        final String redirectUri = "http://localhost:8080/oauth/google-callback";

        GoogleTokenDto.Request googleTokenDto = new GoogleTokenDto.Request(grantType, clientId, clientSecret, code, redirectUri);
        Object googleToken = googleTokenClient.requestGoogleToken(googleTokenDto);
        return googleToken.toString();
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

}
