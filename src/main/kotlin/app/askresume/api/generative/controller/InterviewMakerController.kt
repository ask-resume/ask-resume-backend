package app.askresume.api.generative.controller

import app.askresume.api.generative.vo.InterviewMakerRequest
import app.askresume.api.generative.facade.InterviewMakerFacade
import app.askresume.domain.generative.interview.validator.InterviewValidator
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.domain.locale.validator.LocaleValidator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "generative", description = "생성, 스케줄 대기열에 등록 API")
@RestController
@RequestMapping("/api/generative/interview-maker")
class InterviewMakerController(
    private val interviewValidator: InterviewValidator,
    private val localeValidator: LocaleValidator,
    private val interviewMakerFacade: InterviewMakerFacade
) {

    @Tag(name = "generative")
    @Operation(
        summary = "[interview-maker] 예상 질문, 모범 답안 대기열에 등록 API",
    )
    @PostMapping
    fun submit(
        @Validated @RequestBody request: InterviewMakerRequest
    ): ResponseEntity<Void> {
        interviewValidator.validateDifficultyType(request.difficulty)
        val locale = localeValidator.validateLocaleType(request.language)

        val copyRequest = request.copy(
            language = LocaleType.from(locale).value()
        )

        interviewMakerFacade.saveSubmit(copyRequest)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}