package app.askresume.domain.submit.service

import app.askresume.RANDOM
import app.askresume.domain.member.model.Member
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.domain.submit.exception.SubmitStatusIsNotCompletedException
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitQueryRepository
import app.askresume.domain.submit.repository.SubmitRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

@ExtendWith(MockitoExtension::class)
class SubmitReadOnlyServiceTest {

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Mock
    private lateinit var submitRepository: SubmitRepository

    @Mock
    private lateinit var submitQueryRepository: SubmitQueryRepository

    @InjectMocks
    private lateinit var submitReadOnlyService: SubmitReadOnlyService


    @Test
    fun `최초로 제출된 내용이 있으면 제출건 내용을 가져온다`() {
        // given
        val submittedDto = RANDOM.nextObject(FirstSubmittedDto::class)
        given(submitQueryRepository.findQueryRequestedFirstSubmit()).willReturn(submittedDto)

        // when
        submitReadOnlyService.findRequestedFirstSubmit()

        // then
        then(submitQueryRepository).should().findQueryRequestedFirstSubmit()
    }

    @Test
    fun `자신이 제출한 내용을 리스트로 받아온다`() {
        // given
        val memberId = RANDOM.nextLong()
        val pageable = RANDOM.nextObject(PageRequest::class)

        val list = RANDOM.nextList(SubmitDto::class)
        val page = PageImpl(list, pageable, list.size.toLong())

        given(submitQueryRepository.findQuerySubmitList(memberId, pageable)).willReturn(page)

        // when
        submitReadOnlyService.findSubmitList(memberId, pageable)

        // then
        then(submitQueryRepository).should().findQuerySubmitList(memberId, pageable)
    }


    @Test
    fun `자신이 제출한 제출건 인지 확인한다`() {
        // given
        val memberId = RANDOM.nextLong()
        val submitId = RANDOM.nextLong()

        val member = RANDOM.nextObject(Member::class)
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member))
        given(submitRepository.existsByIdAndMember(submitId, member)).willReturn(true)

        // when
        submitReadOnlyService.isMySubmit(submitId, memberId)

        // then
        then(memberRepository).should().findById(memberId)
        then(submitRepository).should().existsByIdAndMember(submitId, member)
    }

    @Test
    fun `제출건의 서비스 타입을 가져온다`() {
        // given
        val submitId = RANDOM.nextLong()

        val submit = RANDOM.nextObject(Submit::class)
        given(submitRepository.findById(submitId)).willReturn(Optional.of(submit))

        // when
        val serviceType = submitReadOnlyService.findSubmitServiceType(submitId)

        // then
        then(submitRepository).should().findById(submitId)
        assertThat(serviceType).isEqualTo(submit.serviceType)
    }

    @Test
    fun `해당 제출건이 COMPLETED 건 인지 확인한다`() {
        // given
        val submitId = RANDOM.nextLong()
        val submit = RANDOM.nextObject(Submit::class)
        submit.updateStatus(SubmitStatus.COMPLETED)

        given(submitRepository.findById(submitId)).willReturn(Optional.of(submit))

        // when
        submitReadOnlyService.isCompleted(submitId)

        // then
        then(submitRepository).should().findById(submitId)
    }

    @Test
    fun `해당 제출건이 COMPLETED이 아니면, SubmitStatusIsNotCompletedException 예외가 발생한다`() {
        // given
        val submitId = RANDOM.nextLong()
        val submit = RANDOM.nextObject(Submit::class)
        submit.updateStatus(SubmitStatus.WAITING)

        given(submitRepository.findById(submitId)).willReturn(Optional.of(submit))

        // when & then
        assertThatThrownBy { submitReadOnlyService.isCompleted(submitId) }
            .isInstanceOf(SubmitStatusIsNotCompletedException::class.java)
    }

}