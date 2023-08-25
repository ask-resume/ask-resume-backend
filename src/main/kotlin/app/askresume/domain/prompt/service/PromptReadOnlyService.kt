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
class PromptReadOnlyService(
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

        return when (serviceType) {
            ServiceType.INTERVIEW_MAKER -> {
                val prompt = findByPromptType(PromptType.INTERVIEW_MAKER)
                val interviewMakerDto = submitDataMapper.interviewMakerSaveDtoOf(parameter)

                String.format(
                    prompt,
                    interviewMakerDto.jobName,
                    interviewMakerDto.resumeType,
                    interviewMakerDto.difficulty,
                    interviewMakerDto.careerYear,
                    interviewMakerDto.language
                )
            }
            ServiceType.INTERVIEW_MAKER_PDF -> {
                val prompt = findByPromptType(PromptType.INTERVIEW_MAKER_PDF)
                val interviewMakerPdfSaveDto = submitDataMapper.interviewMakerPdfSaveDtoOf(parameter)

                String.format(
                    prompt,
                    interviewMakerPdfSaveDto.jobName,
                    interviewMakerPdfSaveDto.difficulty,
                    interviewMakerPdfSaveDto.careerYear,
                    interviewMakerPdfSaveDto.language
                )
            }
        }
    }
}

