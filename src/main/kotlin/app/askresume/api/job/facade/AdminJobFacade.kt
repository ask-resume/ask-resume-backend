package app.askresume.api.job.facade

import app.askresume.api.job.vo.SaveJobRequest
import app.askresume.domain.job.service.JobCommandService
import app.askresume.global.annotation.Facade
import org.springframework.transaction.annotation.Transactional

@Facade
@Transactional(readOnly = true)
class AdminJobFacade(
    private val jobCommandService: JobCommandService
) {

    @Transactional
    fun save(request: SaveJobRequest) {
        jobCommandService.saveJobs(
            englishJobName = request.englishJobName,
            koreaJobName = request.koreaJobName,
        )
    }

}