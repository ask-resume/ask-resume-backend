package app.askresume.api.resume.controller;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.facade.ResumeFacade;
import app.askresume.api.resume.validator.DifficultyValidator;
import app.askresume.global.model.ApiResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "resume", description = "예상질문생성/조회 API")
@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeFacade resumeFacade;

    private final DifficultyValidator difficultyValidator;

    @Tag(name = "resume")
    @Operation(summary = "예상 질문 생성 API", description = "예상 질문 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
            @ApiResponse(responseCode = "R-001", description = "잘못된 난이도 입니다.")
    })
    @PostMapping("/generate-expected-questions")
    public ResponseEntity<ApiResult<WhatGeneratedResponse>> generate(@Validated @RequestBody GenerateQuestionRequest request)
            throws JsonProcessingException {

        difficultyValidator.validateDifficultyType(request.getDifficulty());

        final WhatGeneratedResponse generate = resumeFacade.generate(request);
        return ResponseEntity.ok((new ApiResult<>(generate)));
    }

}
