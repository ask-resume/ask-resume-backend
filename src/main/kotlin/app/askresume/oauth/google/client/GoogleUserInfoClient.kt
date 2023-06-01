package app.askresume.oauth.google.client

import app.askresume.oauth.google.dto.GoogleUserInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(url = "\${oauth.google.url}", name = "googleUserInfoClient")
interface GoogleUserInfoClient {

    @GetMapping(value = ["v2/userinfo"], consumes = ["application/json"])
    fun getGoogleUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String): GoogleUserInfoResponse

}

