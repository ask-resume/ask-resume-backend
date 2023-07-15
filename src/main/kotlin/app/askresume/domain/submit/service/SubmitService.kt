package app.askresume.domain.submit.service

import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitService(
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository,
) {
    @Transactional
    fun saveSubmit(
        title: String,
        serviceType: ServiceType,
        dataCount: Int,
    ): Long {

        return submitRepository.save(
            Submit(
                title = title,
                serviceType = serviceType,
                dataCount = dataCount
            )
        ).id!!
    }

    @Transactional
    fun updateStatus(submitId: Long, changeStatus: SubmitStatus) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.updateStatus(changeStatus)
    }

    @Transactional
    fun increaseAttemptsAndCheckFailure(submitId: Long) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.plusAttempts()

        if (submit.attempts == 3) {
            updateStatus(
                submitId = submitId,
                changeStatus = SubmitStatus.FAIL,
            )
        }
    }

    @Transactional
    fun verifyAllSubmittedDataSuccess(submitId: Long) {
        val submit = submitRepository.findSubmitById(submitId)

        val successfulSubmitDataCount = submitDataRepository.countBySubmitAndSubmitDataStatus(
            submit = submit,
            submitDataStatus = SubmitDataStatus.SUCCESS,
        )

        val newSubmitStatus = if (successfulSubmitDataCount == submit.dataCount) {
            SubmitStatus.COMPLETED
        } else {
            SubmitStatus.GENERATING
        }

        updateStatus(submitId = submitId, changeStatus = newSubmitStatus)


    }
}