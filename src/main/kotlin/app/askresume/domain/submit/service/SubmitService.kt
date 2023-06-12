package app.askresume.domain.submit.service

import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.model.SubmitData
import app.askresume.domain.submit.repository.SubmitRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitService(
    private val submitRepository: SubmitRepository,
) {

    @Transactional
    fun save(
        title: String,
        serviceType: ServiceType,
        dataCount: Int,
        parameters: List<HashMap<String, Any>>
    ): Long? {
        val submit = Submit(title, serviceType, dataCount)

        submit.addSubmitList(
            parameters.map { param ->
                SubmitData(param)
            }
        )

        return submitRepository.save(submit).id
    }
}