package app.askresume.api.job.controller;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.api.job.facade.JobFacade;
import app.askresume.api.job.validator.JobValidator;
import app.askresume.domain.job.constant.LocaleType;
import app.askresume.global.model.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Tag(name = "job", description = "직업데이터 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JobController {

    private final JobFacade jobFacade;
    private final JobValidator jobValidator;

    @Tag(name = "job")
    @Operation(summary = "언어별로 직업 리스트를 조회하는 API", description = "언어별로 직업 리스트를 조회하는 API")
    @GetMapping("/v1/jobs")
    public ResponseEntity<ApiResult<List<JobResponse>>> findJobs(
            @Parameter(name = "locale", description = "국가코드(ko,en,jp)", required = true)
            @RequestParam @NotBlank String locale) {
        jobValidator.validateLocaleType(locale);
        LocaleType localeType = LocaleType.from(locale);

        return ResponseEntity.ok(new ApiResult<>(jobFacade.findJobs(localeType.name())));
    }
}
