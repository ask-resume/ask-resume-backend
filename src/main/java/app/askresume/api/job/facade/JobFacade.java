package app.askresume.api.job.facade;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.domain.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobFacade {

    private final JobService jobService;

    public List<JobResponse> findJobs(String locale) {
        return jobService.findJobs(locale);
    }
}
