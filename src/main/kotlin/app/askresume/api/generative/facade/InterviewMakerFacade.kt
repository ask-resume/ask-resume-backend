package app.askresume.api.generative.facade

import app.askresume.api.generative.mapper.resumeDataVoListOf
import app.askresume.api.generative.mapper.toCareer
import app.askresume.api.generative.vo.InterviewMakerRequest
import app.askresume.domain.generative.interview.dto.InterviewMakerSaveDto
import app.askresume.domain.job.service.JobReadOnlyService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.service.SubmitCommandService
import app.askresume.domain.submit.service.SubmitDataCommandService
import app.askresume.global.annotation.Facade
import app.askresume.global.resolver.memberinfo.MemberInfo
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.transaction.annotation.Transactional

@Facade
@Transactional(readOnly = true)
class InterviewMakerFacade(
    private val jobReadOnlyService: JobReadOnlyService,
    private val submitCommandService: SubmitCommandService,
    private val submitDataCommandService: SubmitDataCommandService,
    private val objectMapper: ObjectMapper,
) {

    @Transactional
    fun saveManualSubmit(request: InterviewMakerRequest, memberInfo: MemberInfo) {
        val resumeData = resumeDataVoListOf(request.contents)
        val jobMasterName = jobReadOnlyService.findJobMasterName(request.jobId)

        val interviewMakerSaveDtoList = resumeData.map { data ->
            InterviewMakerSaveDto(
                jobName = jobMasterName,
                difficulty = request.difficulty.value(),
                careerYear = toCareer(request.careerYear),
                language = request.language.value(),
                resumeType = data.resumeType,
                content = data.content
            )
        }.toMutableList()

        val parameters = interviewMakerSaveDtoList.map { dto ->
            objectMapper.convertValue(dto, Map::class.java) as Map<String, Any>
        }

        val submitId = submitCommandService.saveSubmit(
            title = generateShortTitle(interviewMakerSaveDtoList[0].content),
            serviceType = ServiceType.INTERVIEW_MAKER,
            dataCount = resumeData.size,
            memberId = memberInfo.memberId,
        )

        submitDataCommandService.addToSubmitData(
            submitId = submitId,
            parameters = parameters,
        )
    }

    /**
     * content에서 title로 사용할 내용을 생략하여 생성합니다.
     */
    private fun generateShortTitle(content: String): String {
        return "${
            content.substring(
                TITLE_SUBSTRING_MIN_SIZE,
                TITLE_SUBSTRING_MAX_SIZE,
            )
        }${ELLIPSIS}"
    }





    companion object {
        private const val TITLE_SUBSTRING_MIN_SIZE: Int = 0
        private const val TITLE_SUBSTRING_MAX_SIZE: Int = 30
        private const val ELLIPSIS: String = "..."
    }
}
