package app.askresume.api.job.vo

import app.askresume.RANDOM
import app.askresume.ValidationUtils
import app.askresume.fixture.SaveJobRequestFixture
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SaveJobRequestTest {

    @Test
    fun `englishJobName와 koreaJobName는 공백일 수 없습니다`() {
        // given
        val requestA = SaveJobRequestFixture.saveJobRequest(englishJobName = " ")
        val requestB = SaveJobRequestFixture.saveJobRequest(koreaJobName = " ")

        // when
        val validateA = ValidationUtils.validate(requestA)
        val validateB = ValidationUtils.validate(requestB)

        // then
        Assertions.assertThat(validateA).isNotEmpty()
        Assertions.assertThat(validateB).isNotEmpty()
    }

    @Test
    fun `englishJobName와 koreaJobName는 150자를 넘을 수 없습니다`() {
        // given
        val requestA = SaveJobRequestFixture.saveJobRequest(englishJobName = RANDOM.nextString(minSize = 151))
        val requestB = SaveJobRequestFixture.saveJobRequest(koreaJobName = RANDOM.nextString(minSize = 151))

        // when
        val validateA = ValidationUtils.validate(requestA)
        val validateB = ValidationUtils.validate(requestB)

        // then
        Assertions.assertThat(validateA).isNotEmpty()
        Assertions.assertThat(validateB).isNotEmpty()
    }

}