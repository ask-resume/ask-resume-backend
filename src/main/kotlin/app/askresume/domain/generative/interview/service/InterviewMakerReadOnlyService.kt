package app.askresume.domain.generative.interview.service

import app.askresume.domain.generative.interview.dto.InterviewMakerDto
import app.askresume.domain.generative.interview.repository.InterviewMakerQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class InterviewMakerReadOnlyService(
    private val interviewMakerQueryRepository: InterviewMakerQueryRepository
) {

    fun findInterviewMaker(submitId: Long): List<InterviewMakerDto> {
        return interviewMakerQueryRepository.findQueryInterviewMaker(submitId)
    }
}