package app.askresume.domain.job.service

import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.findJobMasterById
import app.askresume.domain.job.repository.JobDataRepositoryQuery
import app.askresume.domain.locale.constant.LocaleType
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JobReadOnlyService(
    private val jobMasterRepository: JobMasterRepository,
    private val jobDataRepositoryQuery : JobDataRepositoryQuery,
) {

    fun findJobMasterName(id: Long): String {
        return jobMasterRepository.findJobMasterById(id).masterName
    }

    @Cacheable(cacheNames = ["jobListCache"], key = "#locale.toString()")
    fun findJobs(locale: LocaleType): List<JobDto> {
        return jobDataRepositoryQuery.findJobs(locale)
    }

}