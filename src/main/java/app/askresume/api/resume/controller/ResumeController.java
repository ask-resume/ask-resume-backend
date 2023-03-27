package app.askresume.api.resume.controller;

import app.askresume.api.resume.dto.request.GenerateQuestionRequest;
import app.askresume.api.resume.dto.response.PdfToTextResponse;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.facade.ResumeFacade;
import app.askresume.api.resume.validator.ResumeValidator;
import app.askresume.global.model.ApiResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "resume", description = "예상질문생성/조회 API")
@RestController
@RequestMapping("/api/resume")
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
    @PostMapping("/generate-expected-questions")
    public ResponseEntity<ApiResult<WhatGeneratedResponse>> generate(@Validated @RequestBody GenerateQuestionRequest request)
            throws JsonProcessingException {

        resumeValidator.validateDifficultyType(request.getDifficulty());

        final WhatGeneratedResponse generate = resumeFacade.generate(request);
        return ResponseEntity.ok(new ApiResult<>(generate));
    }

    @Tag(name = "resume")
    @Operation(summary = "이력서를 Text로 전환 API", description = "이력서를 Text로 전환 API")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
            @ApiResponse(responseCode = "500", description = "(F-002) 파일에서 TEXT를 읽는 도중 에러가 발생하였습니다."),
            @ApiResponse(responseCode = "400", description = "(F-001) 허가된 CONTENT_TYPE이 아닙니다.")
    })
    @PostMapping(value = "/to-text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<PdfToTextResponse>> extractTextFromPdf(@Parameter(name = "resume", description = "이력서PDF파일, maxSize: 3MB, 확장자: pdf)", required = true)
                                                                           @RequestPart("resume") MultipartFile file
    ) {
        resumeValidator.validateContentType(file.getContentType());
        return ResponseEntity.ok(new ApiResult<>(resumeFacade.toText(file)));
    }
}
