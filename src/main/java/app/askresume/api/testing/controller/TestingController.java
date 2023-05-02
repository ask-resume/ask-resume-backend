package app.askresume.api.testing.controller;

import app.askresume.api.testing.dto.request.TestingRequest;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "testing", description = "테스트용 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestingController {

    @Tag(name = "testing")
    @GetMapping("/server")
    public void serverError() {
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Tag(name = "testing")
    @GetMapping("/authorization")
    public void authorizationError() {
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Tag(name = "testing")
    @PostMapping("/bind")
    public void bindError(@RequestBody @Validated TestingRequest request) {
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
