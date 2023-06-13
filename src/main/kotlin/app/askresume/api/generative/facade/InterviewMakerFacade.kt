package app.askresume.api.generative.facade

import app.askresume.api.generative.dto.InterviewMakerDto
import app.askresume.api.generative.dto.InterviewMakerRequest
import app.askresume.api.generative.mapper.toCareer
import app.askresume.api.generative.mapper.toResumeData
import app.askresume.domain.job.service.JobService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.service.SubmitService
import app.askresume.global.annotation.Facade
import com.fasterxml.jackson.databind.ObjectMapper

@Facade
class InterviewMakerFacade(
    private val jobService: JobService,
    private val submitService: SubmitService,
    private val objectMapper: ObjectMapper,
) {

    @Suppress("UNCHECKED_CAST")
    fun save(request: InterviewMakerRequest) {
        val resumeData = toResumeData(request.contents)
        val jobMasterName = jobService.findJobMasterNameById(request.jobId)

        val interviewMakerDtoList = resumeData.map { data ->
            InterviewMakerDto(
                jobName = jobMasterName,
                difficulty = request.difficulty,
                careerYear = toCareer(request.careerYear),
                language = request.language,
                resumeType = data.resumeType,
                contents = data.content
            )
        }.toMutableList()

        val parameters = interviewMakerDtoList.map { dto ->
            objectMapper.convertValue(dto, Map::class.java) as HashMap<String, Any>
        }

        submitService.save(
            title = "아직 어떻게 할지 고민중",
            serviceType = ServiceType.INTERVIEW_MAKER,
            dataCount = resumeData.size,
            parameters = parameters,
        )
    }
}
