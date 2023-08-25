package app.askresume.domain.generative.factory

import app.askresume.domain.generative.interview.repository.InterviewMakerRepository
import app.askresume.domain.generative.interview.service.InterviewMakerCommandService
import app.askresume.domain.generative.service.GenerativeCommandService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GenerativeCommandFactory(
    private val objectMapper: ObjectMapper,
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository,

    private val interviewMakerRepository: InterviewMakerRepository
) {

    fun createGenerativeProvider(serviceType: ServiceType): GenerativeCommandService {
        return when (serviceType) {
            ServiceType.INTERVIEW_MAKER, ServiceType.INTERVIEW_MAKER_PDF -> InterviewMakerCommandService(
                objectMapper,
                submitRepository,
                submitDataRepository,
                interviewMakerRepository,
            )
        }
    }
}