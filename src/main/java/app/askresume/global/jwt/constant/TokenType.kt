package app.askresume.global.jwt.constant


enum class TokenType {

    ACCESS, REFRESH;

    companion object {
        fun isAccessToken(tokenType: String) = ACCESS.name == tokenType
    }
}