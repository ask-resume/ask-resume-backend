package app.askresume.domain.submit.service

import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.member.repository.findMemberById
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.domain.submit.exception.SubmitStatusIsNotCompletedException
import app.askresume.domain.submit.repository.SubmitQueryRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.existsSubmitByIdAndMember
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitReadOnlyService(
    private val submitRepository: SubmitRepository,
    private val memberRepository: MemberRepository,

    private val submitQueryRepository: SubmitQueryRepository,
) {
    fun findRequestedFirstSubmit(): FirstSubmittedDto? {
        return submitQueryRepository.findQueryRequestedFirstSubmit()
    }

    fun findSubmitList(
        memberId: Long? = null,
        pageable: Pageable
    ): Page<SubmitDto> {
        return submitQueryRepository.findQuerySubmitList(
            memberId = memberId,
            pageable = pageable,
        )
    }

    fun isMySubmit(submitId: Long, memberId: Long) {
        val member = memberRepository.findMemberById(memberId)
        submitRepository.existsSubmitByIdAndMember(submitId, member)
    }

    fun findSubmitServiceType(submitId: Long) : ServiceType {
        return submitRepository.findSubmitById(submitId).serviceType
    }

    fun isCompleted(submitId: Long) {
        val status = submitRepository.findSubmitById(submitId).submitStatus

        if(status!= SubmitStatus.COMPLETED)
            throw SubmitStatusIsNotCompletedException(status)
    }
}