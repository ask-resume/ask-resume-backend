package app.askresume.web.google;

public record GoogleTokenDto() {

    public record Request(
            String grant_type,
            String client_id,
            String client_secret,
            String code,
            String redirect_uri
    ) {

        public Request(String grant_type, String client_id, String client_secret, String code, String redirect_uri) {
            this.grant_type = grant_type;
            this.client_id = client_id;
            this.client_secret = client_secret;
            this.code = code;
            this.redirect_uri = redirect_uri;
        }
    }

    public record Response(
            String access_token,
            Integer expires_in,
            String scope,
            String token_type,
            String id_token
    ) {
    }

}
