package app.askresume.api.resume.controller

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest
import app.askresume.api.resume.dto.response.WhatGeneratedResponse
import app.askresume.api.resume.facade.ResumeFacade
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.domain.locale.validator.LocaleValidator
import app.askresume.domain.resume.validator.ResumeValidator
import app.askresume.global.model.ApiResult
import app.askresume.global.util.LoggerUtil.log
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "resume", description = "예상질문 & 답변 생성/조회 API")
@RestController
@RequestMapping("/api")
class ResumeController(
    private val resumeFacade: ResumeFacade,
    private val localeValidator: LocaleValidator,
    private val resumeValidator: ResumeValidator,
) {

    val log = log()

    @Tag(name = "resume")
    @Operation(summary = "예상 질문 생성 API", description = "예상 질문 생성 API")
    @ApiResponses(
        ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
        ApiResponse(responseCode = "500", description = "(SYS-002) 스레드 작업 중 문제 발생"),
        ApiResponse(responseCode = "500", description = "(SYS-003) JSON 파싱 중 문제"),
        ApiResponse(responseCode = "400", description = "(RES-001) 잘못된 난이도 유형")
    )
    @PostMapping("/v1/resume/generate")
    fun generate(@Validated @RequestBody request: GenerateExpectedQuestionRequest): ResponseEntity<ApiResult<WhatGeneratedResponse>> {
        resumeValidator.validateDifficultyType(request.difficulty)
        val locale = localeValidator.validateLocaleType(request.locale)

        val copyRequest = request.copy(
            locale = LocaleType.from(locale).value()
        )

        val generate = resumeFacade.generate(copyRequest)
        return ResponseEntity.ok(ApiResult(generate))
    }
}

