package app.askresume.domain.submit.service

import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.repository.query.SubmitQueryRepository
import com.querydsl.core.Tuple
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitQueryService(
    private val submitQueryRepository: SubmitQueryRepository,
) {
    fun findRequestedFirstSubmit(): FirstSubmittedDto? {
        return submitQueryRepository.findRequestedFirstSubmit()
    }


}