package app.askresume.domain.submit.service

import app.askresume.RANDOM
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.repository.SubmitDataRepository
import app.askresume.domain.submit.repository.SubmitRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class SubmitCommandServiceTest {

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Mock
    private lateinit var submitRepository: SubmitRepository

    @Mock
    private lateinit var submitDataRepository: SubmitDataRepository

    @InjectMocks
    private lateinit var submitCommandService: SubmitCommandService


    @Test
    fun `제출건의 상태를 변경한다`() {
        // given
        val submitId = RANDOM.nextLong()
        val changeStatus = RANDOM.nextObject(SubmitStatus::class)

        val submit = RANDOM.nextObject(Submit::class)
        given(submitRepository.findById(submitId)).willReturn(Optional.of(submit))

        // when
        submitCommandService.updateStatus(submitId, changeStatus)

        // then
        then(submitRepository).should().findById(submitId)
    }

}