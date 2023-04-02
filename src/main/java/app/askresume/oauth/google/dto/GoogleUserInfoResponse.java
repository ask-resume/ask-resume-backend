package app.askresume.oauth.google.dto;

public record GoogleUserInfoResponse(
        String email,
        String name,
        String picture,
        String locale
) {
}
