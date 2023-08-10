package app.askresume.api.submit.facade

import app.askresume.api.submit.mapper.SubmitMapper
import app.askresume.api.submit.vo.SubmitDetailResponse
import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.domain.generative.interview.service.InterviewMakerReadOnlyService
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.service.SubmitReadOnlyService
import app.askresume.global.model.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitFacade(
    private val submitReadOnlyService: SubmitReadOnlyService,

    private val interviewMakerReadOnlyService: InterviewMakerReadOnlyService,

    private val submitMapper: SubmitMapper,
) {

    fun findMySubmits(pageable: Pageable, memberId: Long): PageResponse<SubmitResponse> {
        val pagedSubmits = submitReadOnlyService.findSubmitList(
            memberId = memberId,
            pageable = pageable
        )

        return submitMapper.submitResponseOf(pagedSubmits)
    }

    fun findMySubmitsDetail(submitId: Long, memberId: Long): SubmitDetailResponse {
        submitReadOnlyService.isMySubmit(submitId, memberId)
        submitReadOnlyService.isCompleted(submitId)

        return when (val serviceType = submitReadOnlyService.findSubmitServiceType(submitId)) {
            ServiceType.INTERVIEW_MAKER ->
                submitMapper.submitDetailResponseOf(
                    serviceType = serviceType,
                    interviewMakerReadOnlyService.findInterviewMaker(
                        submitId
                    ),
                )
        }
    }

}