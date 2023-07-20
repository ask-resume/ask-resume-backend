package app.askresume.domain.generative.interview.service

import app.askresume.domain.generative.interview.repository.InterviewRepository
import app.askresume.domain.generative.interview.dto.InterviewMakerResultDtoList
import app.askresume.domain.generative.interview.model.ResultInterviewMaker
import app.askresume.domain.generative.service.GenerativeService
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.findSubmitDataById
import app.askresume.external.openai.dto.ChoicesDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class InterviewService(
    private val objectMapper: ObjectMapper,
    private val interviewRepository: InterviewRepository,
    private val submitDataRepository: SubmitDataRepository,
) : GenerativeService {

    @Transactional
    override fun saveGenerativeResult(
        submitDataId: Long,
        choices: List<ChoicesDto>
    ) {
        val submitData = submitDataRepository.findSubmitDataById(submitDataId)
        val result = objectMapper.readValue(choices[0].message.content, InterviewMakerResultDtoList::class.java)

        val resultInterviewMakerList = result.interviews.map { dto ->
            ResultInterviewMaker(
                question = dto.question,
                bestAnswer = dto.bestAnswer,
                submitData = submitData
            )
        }

        interviewRepository.saveAll(resultInterviewMakerList)
    }
}