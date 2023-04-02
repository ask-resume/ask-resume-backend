package app.askresume.domain.job.service;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.domain.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;


    public List<JobResponse> findJobs(String locale) {
        return jobRepository.findJobs(locale);
    }
}
