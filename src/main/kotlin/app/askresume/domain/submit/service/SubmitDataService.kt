package app.askresume.domain.submit.service

import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.model.SubmitData
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import app.askresume.domain.submit.repository.findSubmitDataById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitDataService(
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository
) {

    @Transactional
    fun addToSubmitData(
        submitId: Long,
        parameters: List<Map<String, Any>>
    ) {
        val submit = submitRepository.findSubmitById(submitId)

        parameters.map { param ->
            submitDataRepository.save(SubmitData(param, submit))
        }
    }

    @Transactional
    fun updateStatus(
        submitDataId: Long,
        changeStatus: SubmitDataStatus
    ) {
        val submitData = submitDataRepository.findSubmitDataById(submitDataId)
        submitData.updateStatus(changeStatus)
    }

}