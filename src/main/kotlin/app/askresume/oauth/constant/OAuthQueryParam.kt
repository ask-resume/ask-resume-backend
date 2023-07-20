package app.askresume.oauth.constant

enum class OAuthQueryParam(
    val key: String,
) {

    CLIENT_ID("client_id"),
    REDIRECT_URI("redirect_uri"),
    RESPONSE_TYPE("response_type"),
    SCOPE("scope"),

}