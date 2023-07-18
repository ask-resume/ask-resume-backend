package app.askresume.domain.submit.service

import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitService(
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun saveSubmit(
        title: String,
        serviceType: ServiceType,
        dataCount: Int,
        memberId: Long,
    ): Long {
        val member = memberRepository.getReferenceById(memberId)

        return submitRepository.save(
            Submit(
                title = title,
                serviceType = serviceType,
                dataCount = dataCount,
                member = member
            )
        ).id!!
    }

    @Transactional
    fun updateStatus(submitId: Long, changeStatus: SubmitStatus) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.updateStatus(changeStatus)
    }

    @Transactional
    fun increaseAttemptsAndCheckFailure(submitId: Long) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.plusAttempts()

        if (submit.attempts == 3) {
            updateStatus(
                submitId = submitId,
                changeStatus = SubmitStatus.FAIL,
            )
        }
    }

    @Transactional
    fun verifyAllSubmittedDataSuccess(submitId: Long) {
        val submit = submitRepository.findSubmitById(submitId)

        val successfulSubmitDataCount = submitDataRepository.countBySubmitAndSubmitDataStatus(
            submit = submit,
            submitDataStatus = SubmitDataStatus.SUCCESS,
        )

        val newSubmitStatus = if (successfulSubmitDataCount == submit.dataCount) {
            SubmitStatus.COMPLETED
        } else {
            SubmitStatus.GENERATING
        }

        updateStatus(submitId = submitId, changeStatus = newSubmitStatus)


    }

    /**
     * 멤버의 ID (PK) 와 Pageable을 제공하면 멤버 개인의 제출 정보 목록이 페이징되어 반환됩니다.
     */
    fun findSubmitsByMemberId(memberId: Long, pageable: Pageable): Page<Submit> {
        return submitRepository.findAllByMemberId(memberId, pageable)
    }

}