package app.askresume.global.resolver.token

data class TokenDto(
    val authorizationHeader: String,
    val token: String,
)