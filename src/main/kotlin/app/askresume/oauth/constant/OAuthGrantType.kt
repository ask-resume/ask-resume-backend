package app.askresume.oauth.constant

enum class OAuthGrantType(
    val value: String,
) {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
}