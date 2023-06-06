package app.askresume.oauth.constant

enum class OAuthQueryParam(
    val key: String,
) {

    GRANT_TYPE("grant_type"),
    CODE("code"),
    CLIENT_ID("client_id"),
    CLIENT_SECRET("client_secret"),
    REDIRECT_URI("redirect_uri"),
    RESPONSE_TYPE("response_type"),
    SCOPE("scope"),

}