package app.askresume.domain.submit.constant

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ServiceTypeTest {

    @Test
    fun `String Type의 ServiceType 이름을 전달하면 ServiceType enum을 반환한다`() {
        // given
        val type = "INTERVIEW_MAKER"

        // when
        val from = ServiceType.from(type)

        // then
        Assertions.assertThat(from).isEqualTo(ServiceType.INTERVIEW_MAKER)
    }

}