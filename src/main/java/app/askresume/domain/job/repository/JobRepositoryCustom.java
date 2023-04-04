package app.askresume.domain.job.repository;

import app.askresume.api.job.dto.response.JobResponse;

import java.util.List;

public interface JobRepositoryCustom {

    List<JobResponse> findJobs(String locale);

}
