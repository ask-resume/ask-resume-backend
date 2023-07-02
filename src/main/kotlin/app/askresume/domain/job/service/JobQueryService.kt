package app.askresume.domain.job.service

import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.repository.query.JobDataRepositoryQuery
import app.askresume.domain.locale.constant.LocaleType
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JobQueryService(
    private val jobDataRepositoryQuery : JobDataRepositoryQuery,
) {

    @Cacheable(cacheNames = ["jobListCache"], key = "#locale.toString()")
    fun findJobs(locale: LocaleType): List<JobDto> {
        return jobDataRepositoryQuery.findJobs(locale)
    }

}