package app.askresume.domain.submit.service

import app.askresume.api.generative.dto.InterviewMakerDto
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.model.SubmitData
import app.askresume.domain.submit.repository.SubmitRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitService(
    private val submitRepository: SubmitRepository,
    private val objectMapper: ObjectMapper,
) {

    @Transactional
    fun save(
        title: String,
        serviceType: ServiceType,
        dataCount: Int,
        submitList: MutableList<InterviewMakerDto>
    ): Long? {

        val submit = Submit(title, serviceType, dataCount)

        val submitDataList = submitList.map { dto ->
            val parameter = objectMapper.convertValue(dto, Map::class.java)
            SubmitData(parameter as HashMap<String, Any>)
        }

        submit.addSubmitList(submitDataList)

        return submitRepository.save(submit).id
    }
}