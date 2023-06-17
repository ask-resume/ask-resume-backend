package app.askresume.api.generative.controller

import app.askresume.api.generative.dto.InterviewMakerRequest
import app.askresume.api.generative.facade.InterviewMakerFacade
import app.askresume.domain.generative.interview.validator.InterviewValidator
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.domain.locale.validator.LocaleValidator
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/generative/interview-maker")
class InterviewMakerController(
    private val interviewValidator : InterviewValidator,
    private val localeValidator : LocaleValidator,
    private val interviewMakerFacade: InterviewMakerFacade
) {

    @PostMapping
    fun submit(
        @Validated @RequestBody request : InterviewMakerRequest,
        @MemberInfo memberInfoDto: MemberInfoDto,
    ) : ResponseEntity<Void> {
        interviewValidator.validateDifficultyType(request.difficulty)
        val locale = localeValidator.validateLocaleType(request.language)

        val copyRequest = request.copy(
            language = LocaleType.from(locale).value()
        )

        interviewMakerFacade.saveSubmit(copyRequest, memberInfoDto)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}