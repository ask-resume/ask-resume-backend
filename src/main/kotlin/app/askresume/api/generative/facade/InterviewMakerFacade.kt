package app.askresume.api.generative.facade

import app.askresume.api.generative.mapper.toCareer
import app.askresume.api.generative.mapper.toResumeData
import app.askresume.api.generative.vo.InterviewMakerRequest
import app.askresume.domain.generative.interview.dto.InterviewMakerDto
import app.askresume.domain.job.service.JobService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.service.SubmitDataService
import app.askresume.domain.submit.service.SubmitService
import app.askresume.global.annotation.Facade
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.transaction.annotation.Transactional

@Facade
@Transactional(readOnly = true)
class InterviewMakerFacade(
    private val jobService: JobService,
    private val submitService: SubmitService,
    private val submitDataService: SubmitDataService,
    private val objectMapper: ObjectMapper,
) {

    private val TITLE_SUBSTRING_MIN_SIZE: Int = 0
    private val TITLE_SUBSTRING_MAX_SIZE: Int = 30

    @Transactional
    fun saveSubmit(request: InterviewMakerRequest) {
        val resumeData = toResumeData(request.contents)
        val jobMasterName = jobService.findJobMasterNameById(request.jobId)

        val interviewMakerDtoList = resumeData.map { data ->
            InterviewMakerDto(
                jobName = jobMasterName,
                difficulty = request.difficulty,
                careerYear = toCareer(request.careerYear),
                language = request.language,
                resumeType = data.resumeType,
                content = data.content
            )
        }.toMutableList()

        val parameters = interviewMakerDtoList.map { dto ->
            objectMapper.convertValue(dto, Map::class.java) as Map<String, Any>
        }

        val submitId = submitService.saveSubmit(
            title = "${
                interviewMakerDtoList[0].content.substring(
                    TITLE_SUBSTRING_MIN_SIZE,
                    TITLE_SUBSTRING_MAX_SIZE
                )
            }...",
            serviceType = ServiceType.INTERVIEW_MAKER,
            dataCount = resumeData.size,
        )

        submitDataService.addToSubmitData(
            submitId = submitId,
            parameters = parameters,
        )
    }
}
