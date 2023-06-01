package app.askresume.web.google

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(url = "\${oauth.google.client-url}", name = "googleTokenClient")
interface GoogleTokenClient {

    @PostMapping(value = ["/token"], consumes = ["application/json"])
    fun requestGoogleToken(@RequestBody request: GoogleTokenDto.Request): Any

}
