package app.askresume.api.generative.vo

import app.askresume.ValidationUtils
import app.askresume.domain.generative.interview.constant.DifficultyType
import app.askresume.domain.locale.constant.LocaleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InformationRequestTest {

    @Test
    fun `경력이 30년을 초과하면 에러가 발생한다`() {
        // given
        val request = InformationRequest(
            jobId = 1,
            difficulty = DifficultyType.MEDIUM,
            careerYear = 31,
            language = LocaleType.KO
        )

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }
}