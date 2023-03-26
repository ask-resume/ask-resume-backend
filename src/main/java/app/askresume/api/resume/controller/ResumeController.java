package app.askresume.api.resume.controller;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.api.resume.facade.ResumeFacade;
import app.askresume.global.model.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeFacade resumeFacade;

    @PostMapping("/generate-expected-questions")
    public ResponseEntity<?> generate(@Validated @RequestBody GenerateQuestionRequest request) {
        resumeFacade.generate(request);
        return ResponseEntity.ok(new ApiResult<>("test"));
    }

}
