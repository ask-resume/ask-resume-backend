package app.askresume.domain.job.repository

import app.askresume.api.job.dto.response.JobResponse
import app.askresume.domain.locale.constant.LocaleType


interface JobRepositoryCustom {

    fun findJobs(locale: LocaleType): List<JobResponse>

}