package app.askresume.api.submit.facade

import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.api.submit.mapper.toSubmitResponse
import app.askresume.domain.submit.service.SubmitService
import app.askresume.global.model.PageResult
import app.askresume.global.resolver.memberinfo.MemberInfo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitFacade(
    private val submitService: SubmitService,
) {

    fun findMySubmits(pageable: Pageable, memberInfo: MemberInfo): PageResult<SubmitResponse> {
        val memberId = memberInfo.memberId
        val pagedSubmits = submitService.findSubmitsByMemberId(memberId, pageable)

        return PageResult(pagedSubmits.map { toSubmitResponse(it) })
    }

}