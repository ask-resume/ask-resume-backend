package app.askresume.oauth.google.dto

data class GoogleUserInfoResponse(
    val email: String,
    val name: String,
    val picture: String,
    val locale: String,
)

