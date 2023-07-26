package app.askresume.domain.submit.service

import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.domain.submit.repository.SubmitQueryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitReadOnlyService(
    private val submitQueryRepository: SubmitQueryRepository,
) {
    fun findRequestedFirstSubmit(): FirstSubmittedDto? {
        return submitQueryRepository.findQueryRequestedFirstSubmit()
    }

    fun findSubmitList(
        memberId: Long? = null,
        pageable: Pageable
    ): Page<SubmitDto> {
        return submitQueryRepository.findQuerySubmitList(
            memberId = memberId,
            pageable = pageable,
        )
    }
}