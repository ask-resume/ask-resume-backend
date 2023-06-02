package app.askresume.domain.job.service

import app.askresume.api.job.dto.response.JobResponse
import app.askresume.domain.job.model.Job
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.JobRepository
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.EntityNotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JobService(
    private val jobRepository: JobRepository,
    private val jobMasterRepository: JobMasterRepository,
) {

    fun saveJobMaster(jobMaster: JobMaster): JobMaster {
        return jobMasterRepository.save(jobMaster)
    }

    fun saveJob(job: Job): Job {
        return jobRepository.save(job)
    }

    @Cacheable(cacheNames = ["jobListCache"], key = "#locale.toString()")
    fun findJobs(locale: LocaleType): List<JobResponse> {
        return jobRepository.findJobs(locale)
    }

    fun findJobNameById(jobId: Long): JobMaster {
        return jobMasterRepository.findByIdOrNull(jobId)
            ?: throw EntityNotFoundException(ErrorCode.JOB_NOT_EXISTS)
    }
}
