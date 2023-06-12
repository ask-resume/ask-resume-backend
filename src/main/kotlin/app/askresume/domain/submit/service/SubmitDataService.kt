package app.askresume.domain.submit.service

import app.askresume.domain.submit.repository.SubmitDataRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitDataService(
    private val submitDataRepository: SubmitDataRepository
) {

}