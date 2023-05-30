package app.askresume.web.google

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class GoogleController(
    private val googleTokenClient: GoogleTokenClient,
) {

    @Value("\${google.client.id}")
    private lateinit var clientId: String

    @Value("\${google.client.secret}")
    private lateinit var clientSecret: String

    @GetMapping("/oauth/google-callback")
    @ResponseBody
    fun loginCallback(code: String): String {
        val grantType = "authorization_code"
        val redirectUri = "http://localhost:8080/oauth/google-callback"

        val googleTokenDto = GoogleTokenDto.Request(grantType, clientId, clientSecret, code, redirectUri)
        val googleToken = googleTokenClient.requestGoogleToken(googleTokenDto)
        return googleToken.toString()
    }

    @GetMapping("/login")
    fun login(): String {
        return "loginForm"
    }
}

