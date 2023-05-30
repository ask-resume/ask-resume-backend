package app.askresume.web.google

class GoogleTokenDto {

    data class Request(
        val grant_type: String,
        val client_id: String,
        val client_secret: String,
        val code: String,
        val redirect_uri: String,
    )

    data class Response(
        val access_token: String,
        val expires_in: Int,
        val scope: String,
        val token_type: String,
        val id_token: String,
    )

}

