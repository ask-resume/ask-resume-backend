package app.askresume.api.generative.vo

import app.askresume.ValidationUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import app.askresume.InformationRequestFixture

class InformationRequestTest {

    @Test
    fun `경력이 30년을 초과하면 에러가 발생한다`() {
        // given
        val request = InformationRequestFixture.informationRequest(careerYear = 31)

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }
}