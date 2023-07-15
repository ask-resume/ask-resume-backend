package app.askresume.domain.prompt.service

import app.askresume.domain.prompt.constant.PromptType
import app.askresume.domain.prompt.repository.PromptRepository
import app.askresume.domain.prompt.repository.findPromptByPromptType
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.mapper.SubmitDataMapper
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class PromptService(
    private val promptRepository: PromptRepository,
    private val submitDataMapper: SubmitDataMapper,
) {

    val log = logger()

    @Cacheable(cacheNames = ["promptCache"], key = "#promptType.toString()")
    fun findByPromptType(promptType: PromptType): String {
        return promptRepository.findPromptByPromptType(promptType).content
    }

    fun findPromptAndFormatting(
        serviceType: ServiceType,
        parameter: Map<String, Any>
    ): String {

        // TODO 인터페이스 활용해야함
        if (serviceType == ServiceType.INTERVIEW_MAKER) {
            val prompt = findByPromptType(PromptType.INTERVIEW_MAKER)
            val interviewMakerDto = submitDataMapper.mapToInterviewMakerDto(parameter)

            val bindingPrompt = String.format(
                prompt,
                interviewMakerDto.jobName,
                interviewMakerDto.resumeType,
                interviewMakerDto.difficulty,
                interviewMakerDto.careerYear,
                interviewMakerDto.language
            )
            log.debug(bindingPrompt)

            return bindingPrompt
        } else {
            // TODO 나중에 바꿔야함
            throw RuntimeException("나중에 바꿔야함")
        }
    }
}

