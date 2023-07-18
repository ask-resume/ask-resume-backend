package app.askresume.domain.generative.factory

import app.askresume.domain.generative.interview.repository.InterviewRepository
import app.askresume.domain.generative.interview.service.InterviewService
import app.askresume.domain.generative.service.GenerativeService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.repository.SubmitDataRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class GenerativeFactory(
    private val objectMapper: ObjectMapper,
    private val submitDataRepository: SubmitDataRepository,

    private val interviewRepository: InterviewRepository
) {

    fun createGenerativeProvider(serviceType: ServiceType): GenerativeService {
        return when (serviceType) {
            ServiceType.INTERVIEW_MAKER -> InterviewService(
                objectMapper,
                interviewRepository,
                submitDataRepository,
            )
        }
    }
}