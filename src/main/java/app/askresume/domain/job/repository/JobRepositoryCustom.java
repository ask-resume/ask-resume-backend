package app.askresume.domain.job.repository;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.domain.locale.constant.LocaleType;

import java.util.List;

public interface JobRepositoryCustom {

    List<JobResponse> findJobs(LocaleType locale);

}
