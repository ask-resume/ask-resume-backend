package app.askresume.domain.submit.service

import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitService(
    private val submitRepository: SubmitRepository,
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
}