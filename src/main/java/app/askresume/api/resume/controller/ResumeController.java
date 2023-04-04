package app.askresume.api.resume.controller;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.facade.ResumeFacade;
import app.askresume.api.resume.validator.ResumeValidator;
import app.askresume.global.model.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "resume", description = "예상질문생성/조회 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeFacade resumeFacade;
    private final ResumeValidator resumeValidator;

    @Tag(name = "resume")
    @Operation(summary = "예상 질문 생성 API", description = "예상 질문 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
            @ApiResponse(responseCode = "400", description = "(R-001) 잘못된 난이도 입니다.")
    })
    @PostMapping("/v1/resume/generate")
    public ResponseEntity<ApiResult<WhatGeneratedResponse>> generate(@Validated @RequestBody GenerateExpectedQuestionRequest request) {

        resumeValidator.validateDifficultyType(request.difficulty());

        final WhatGeneratedResponse generate = resumeFacade.generate(request);
        return ResponseEntity.ok(new ApiResult<>(generate));
    }

}
