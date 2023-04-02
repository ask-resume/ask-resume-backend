package app.askresume.web.google;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "https://oauth2.googleapis.com", name = "googleTokenClient")
public interface GoogleTokenClient {

    @PostMapping(value = "/token", consumes = "application/json")
    Object requestGoogleToken(@RequestBody GoogleTokenDto.Request request);


}
