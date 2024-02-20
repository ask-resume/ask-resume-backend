package app.askresume.api.member.vo

import app.askresume.RANDOM
import app.askresume.ValidationUtils
import app.askresume.fixture.ModifyInfoRequestFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ModifyInfoRequestTest {

    @Test
    fun `프로필 주소는 URL 형태어야 합니다`() {
        // given
        val request = ModifyInfoRequestFixture.modifyInfoRequest(profile = "120x120.jpg")

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }

    @Test
    fun `사용자 이름은 공백을 허용하지 않습니다`() {
        // given
        val request = ModifyInfoRequestFixture.modifyInfoRequest(username = " ")

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }

    @Test
    fun `사용자 이름은 최소 2글자 이상이어야 합니다`() {
        // given
        val request = ModifyInfoRequestFixture.modifyInfoRequest(username = "j")

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }

    @Test
    fun `사용자 이름은 20글자를 초과할 수 없습니다`() {
        // given
        val request = ModifyInfoRequestFixture.modifyInfoRequest(username = RANDOM.nextString(minSize = 21))

        // when
        val validate = ValidationUtils.validate(request)

        // then
        assertThat(validate).isNotEmpty()
    }

}