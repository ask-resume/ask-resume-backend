package app.askresume.api.generative.facade

import app.askresume.api.generative.dto.InterviewMakerDto
import app.askresume.api.generative.dto.InterviewMakerRequest
import app.askresume.api.generative.mapper.toCareer
import app.askresume.api.generative.mapper.toResumeData
import app.askresume.domain.job.service.JobService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.service.SubmitDataService
import app.askresume.domain.submit.service.SubmitService
import app.askresume.global.annotation.Facade
import org.springframework.transaction.annotation.Transactional

@Facade
class InterviewMakerFacade(
    private val jobService: JobService,
    private val submitService: SubmitService,
    private val submitDataService: SubmitDataService,
) {

    @Transactional
    fun save(request: InterviewMakerRequest) {
        val resumeData = toResumeData(request.contents)
        //val jobMasterName = jobService.findJobMasterNameById(request.jobId)
        val jobMasterName = "테스트"

        val list = resumeData.map { data ->
            InterviewMakerDto(
                jobName = jobMasterName,
                difficulty = request.difficulty,
                careerYear = toCareer(request.careerYear),
                language = request.language,
                resumeType = data.resumeType,
                content = data.content
            )
        }.toMutableList()

        submitService.save(
            title = "아직 어떻게 할지 고민중",
            serviceType = ServiceType.INTERVIEW_MAKER,
            dataCount = resumeData.size,
            submitList = list,
        )
    }
}
