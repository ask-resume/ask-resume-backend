package app.askresume.domain.submit.service

import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.exception.ContentLengthOverException
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import app.askresume.domain.submit.repository.findSubmitById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubmitCommandService(
    private val submitRepository: SubmitRepository,
    private val submitDataRepository: SubmitDataRepository,
    private val memberRepository: MemberRepository,
) {
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
        ).id !!
    }

    fun updateStatus(submitId: Long, changeStatus: SubmitStatus) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.updateStatus(changeStatus)
    }

    fun increaseAttemptsAndCheckFailure(submitId: Long) {
        val submit = submitRepository.findSubmitById(submitId)
        submit.increaseAttempts()

        if (submit.attempts == 3) {
            updateStatus(
                submitId = submitId,
                changeStatus = SubmitStatus.FAIL,
            )
        }
    }

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

    fun checkLength(content: String) {
        val contentLength = content.length
        if (contentLength > PDF_CONTENT_LENGTH) {
            throw ContentLengthOverException(contentLength)
        }
    }

    companion object {
        private const val PDF_CONTENT_LENGTH: Int = 8_000
    }

}