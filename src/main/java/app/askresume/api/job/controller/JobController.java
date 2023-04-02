package app.askresume.api.job.controller;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.api.job.facade.JobFacade;
import app.askresume.api.job.validator.JobValidator;
import app.askresume.domain.job.constant.LocaleType;
import app.askresume.global.model.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JobController {

    private final JobFacade jobFacade;
    private final JobValidator jobValidator;

    @GetMapping("/v1/jobs")
    public ResponseEntity<ApiResult<List<JobResponse>>> findJobs(@RequestParam String locale) {
        jobValidator.validateLocaleType(locale);
        LocaleType localeType = LocaleType.from(locale);

        return ResponseEntity.ok(new ApiResult<>(jobFacade.findJobs(localeType.name())));
    }
}
