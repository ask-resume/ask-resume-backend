package app.askresume.domain.generative.interview.service

import app.askresume.domain.generative.interview.repository.InterviewMakerRepository
import app.askresume.domain.generative.interview.dto.InterviewMakerResultDtoList
import app.askresume.domain.generative.interview.model.ResultInterviewMaker
import app.askresume.domain.generative.service.GenerativeCommandService
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import app.askresume.domain.submit.repository.findSubmitDataById
import app.askresume.external.openai.dto.ChoicesDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InterviewMakerCommandService(
    private val objectMapper: ObjectMapper,
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository,
    private val interviewMakerRepository: InterviewMakerRepository,
) : GenerativeCommandService {

    override fun saveGenerativeResult(
        submitId: Long,
        submitDataId: Long,
        choices: List<ChoicesDto>
    ) {
        val submit = submitRepository.findSubmitById(submitId)
        val submitData = submitDataRepository.findSubmitDataById(submitDataId)
        val result = objectMapper.readValue(choices[0].message.content, InterviewMakerResultDtoList::class.java)

        val resultInterviewMakerList = result.interviews.map { dto ->
            ResultInterviewMaker(
                question = dto.question,
                bestAnswer = dto.bestAnswer,
                submit = submit,
                submitData = submitData
            )
        }

        interviewMakerRepository.saveAll(resultInterviewMakerList)
    }
}