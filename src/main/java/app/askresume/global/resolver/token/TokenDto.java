package app.askresume.global.resolver.token;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private String authorizationHeader;

    private String token;
}
