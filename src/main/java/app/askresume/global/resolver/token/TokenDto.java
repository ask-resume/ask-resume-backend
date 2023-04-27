package app.askresume.global.resolver.token;


public record TokenDto(
        String authorizationHeader,
        String token
) {

    public TokenDto(String authorizationHeader, String token) {
        this.authorizationHeader = authorizationHeader;
        this.token = token;
    }
}
