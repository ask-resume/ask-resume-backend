package app.askresume.domain.submit.service

import app.askresume.api.generative.dto.InterviewMakerDto
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.model.SubmitData
import app.askresume.domain.submit.repository.SubmitDataRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitDataService(
    private val submitDataRepository: SubmitDataRepository,
    private val objectMapper: ObjectMapper
) {

    @Transactional
    fun save(submit: Submit, list: MutableList<InterviewMakerDto>) {
        val submitDataList = list.map { dto ->
            val parameter = objectMapper.convertValue(dto, Map::class.java)
            SubmitData(submit, parameter as HashMap<String, Any>)
        }

        submitDataRepository.saveAll(submitDataList)
    }
}