package app.askresume.domain.submit.model

import app.askresume.RANDOM
import app.askresume.domain.submit.constant.SubmitStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SubmitTest {

    @Test
    fun `제출건의 상태를 변경하면, 변경한 상태값이 저장된다`() {
        // given
        val changeStatus = RANDOM.nextObject(SubmitStatus::class)
        val submit = RANDOM.nextObject(Submit::class)

        // when
        submit.updateStatus(changeStatus)

        // then
        assertThat(submit.submitStatus).isEqualTo(changeStatus)
    }

    @Test
    fun `제출한 주문건의 재시도 횟수를 1증가 시킨다`() {
        // given
        val submit = RANDOM.nextObject(Submit::class)
        val incrementedValues = submit.attempts + 1

        // when
        submit.increaseAttempts()

        // then
        assertThat(submit.attempts).isEqualTo(incrementedValues)
    }

}