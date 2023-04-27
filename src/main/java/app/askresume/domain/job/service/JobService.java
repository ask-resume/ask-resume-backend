package app.askresume.domain.job.service;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.domain.job.model.Job;
import app.askresume.domain.job.model.JobMaster;
import app.askresume.domain.job.repository.JobMasterRepository;
import app.askresume.domain.job.repository.JobRepository;
import app.askresume.domain.locale.constant.LocaleType;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final JobMasterRepository jobMasterRepository;

    public JobMaster saveJobMaster(JobMaster jobMaster) {
        return jobMasterRepository.save(jobMaster);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public List<JobResponse> findJobs(LocaleType locale) {
        return jobRepository.findJobs(locale);
    }

    public JobMaster findJobNameById(Long jobId) {
        return jobMasterRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.JOB_NOT_EXISTS));
    }
}
