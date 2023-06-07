package app.askresume.global.jwt.constant


enum class JwtTokenType(
    val cookieName: String,
) {

    ACCESS("access_token"),
    REFRESH("refresh_token"),
    ;

    companion object {
        fun isAccessToken(tokenType: String) = ACCESS.name == tokenType
    }
}