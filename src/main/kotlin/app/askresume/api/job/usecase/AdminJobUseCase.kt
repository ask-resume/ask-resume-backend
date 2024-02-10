package app.askresume.api.job.usecase

import app.askresume.api.job.vo.SaveJobRequest
import app.askresume.domain.job.service.JobCommandService
import app.askresume.global.annotation.UseCase
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(readOnly = true)
class AdminJobUseCase(
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