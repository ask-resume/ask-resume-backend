package app.askresume.domain.submit.service

import app.askresume.domain.submit.model.SubmitData
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.HashMap

@Service
@Transactional(readOnly = true)
class SubmitDataService(
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository
) {

    @Transactional
    fun addToSubmitData(
        submitId: Long,
        parameters: List<HashMap<String, Any>>
    ) {
        val submit = submitRepository.findSubmitById(submitId)

        parameters.map { param ->
            submitDataRepository.save(SubmitData(param, submit))
        }
    }

}