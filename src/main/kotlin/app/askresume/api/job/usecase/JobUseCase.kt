package app.askresume.api.job.usecase

import app.askresume.api.job.mapper.JobMapper
import app.askresume.api.job.vo.JobResponse
import app.askresume.domain.job.service.JobReadOnlyService
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.global.annotation.Facade

@Facade
class JobUseCase(
    private val jobReadOnlyService: JobReadOnlyService,
    private val jobMapper: JobMapper,
) {

    fun findJobs(locale: LocaleType): List<JobResponse> {
        return jobMapper.jobResponseListOf(jobReadOnlyService.findJobs(locale))
    }
}