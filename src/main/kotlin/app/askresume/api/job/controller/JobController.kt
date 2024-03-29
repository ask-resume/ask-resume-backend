package app.askresume.api.job.controller

import app.askresume.api.job.usecase.JobUseCase
import app.askresume.api.job.vo.JobResponse
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.domain.locale.validator.LocaleValidator
import app.askresume.api.ApiResult
import app.askresume.global.util.LoggerUtil.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "job", description = "직업데이터 API (사용자)")
@RestController
@RequestMapping("/api")
class JobController(
    private val jobUseCase: JobUseCase,
    private val localeValidator: LocaleValidator,
) {

    val log = logger()

    @Tag(name = "job")
    @Operation(summary = "언어별로 직업 리스트를 조회하는 API", description = "언어별로 직업 리스트를 조회하는 API")
    @GetMapping("/v1/jobs")
    fun findJobs(): ResponseEntity<ApiResult<List<JobResponse>>> {
        var language = LocaleContextHolder.getLocale().language
        language = localeValidator.validateLocaleType(language)

        val localeType = LocaleType.from(language)

        return ResponseEntity.ok(ApiResult(jobUseCase.findJobs(localeType)))
    }

}

