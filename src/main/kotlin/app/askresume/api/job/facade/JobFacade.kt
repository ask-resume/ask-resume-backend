package app.askresume.api.job.facade

import app.askresume.api.job.dto.response.JobResponse
import app.askresume.domain.job.service.JobService
import app.askresume.domain.locale.constant.LocaleType
import org.springframework.stereotype.Service

@Service
class JobFacade(
    private val jobService: JobService,
) {

    fun findJobs(locale: LocaleType): List<JobResponse> {
        return jobService.findJobs(locale)
    }

}

