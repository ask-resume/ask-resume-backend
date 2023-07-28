package app.askresume.api.submit.facade

import app.askresume.api.submit.mapper.SubmitMapper
import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.api.submit.vo.SubmitServiceTypeResponse
import app.askresume.domain.submit.service.SubmitReadOnlyService
import app.askresume.domain.submit.service.SubmitCommandService
import app.askresume.global.model.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitFacade(
    private val submitCommandService: SubmitCommandService,
    private val submitReadOnlyService: SubmitReadOnlyService,

    private val submitMapper: SubmitMapper,
) {

    fun findMySubmits(pageable: Pageable, memberId: Long): PageResponse<SubmitResponse> {
        val pagedSubmits = submitReadOnlyService.findSubmitList(
            memberId = memberId,
            pageable = pageable
        )

        return submitMapper.pageSubmitDtoToSubmitResponse(pagedSubmits)
    }

    fun findMySubmitsDetail(submitId: Long, memberId: Long): SubmitServiceTypeResponse {
        submitReadOnlyService.isMySubmit(submitId, memberId)
        submitReadOnlyService.isCompleted(submitId)

        return SubmitServiceTypeResponse(
            serviceType = submitReadOnlyService.findSubmitServiceType(submitId)
        )
    }

}