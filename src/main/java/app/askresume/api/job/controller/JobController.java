package app.askresume.api.job.controller;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.api.job.facade.JobFacade;
import app.askresume.domain.locale.constant.LocaleType;
import app.askresume.domain.locale.validator.LocaleValidator;
import app.askresume.global.model.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "job", description = "직업데이터 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JobController {

    private final JobFacade jobFacade;
    private final LocaleValidator localeValidator;

    @Tag(name = "job")
    @Operation(summary = "언어별로 직업 리스트를 조회하는 API", description = "언어별로 직업 리스트를 조회하는 API")
    @GetMapping("/v1/jobs")
    public ResponseEntity<ApiResult<List<JobResponse>>> findJobs() {

        final String language = LocaleContextHolder.getLocale().getLanguage();

        localeValidator.validateLocaleType(language);
        LocaleType localeType = LocaleType.from(language);

        return ResponseEntity.ok(new ApiResult<>(jobFacade.findJobs(localeType.name())));
    }
}
