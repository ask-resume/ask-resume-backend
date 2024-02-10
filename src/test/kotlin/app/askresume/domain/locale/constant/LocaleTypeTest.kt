package app.askresume.domain.locale.constant

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LocaleTypeTest {

    @DisplayName("String Type의 Locale 이름을 전달하면 LocaleType enum을 반환한다.")
    @Test
    fun fromTest() {
        // given
        val type = "ko"

        // when
        val from = LocaleType.from(type)

        // then
        assertThat(from).isEqualTo(LocaleType.KO)
    }

    @DisplayName("String Type의 Locale 이름을 전달하였을때, 해당하는 Locale이 존재하면 True를 반환한다.")
    @Test
    fun isLocaleTypeTrueTest() {
        // given
        val type = "ko"

        // when
        val isLocaleType = LocaleType.isLocaleType(type)

        // then
        assertThat(isLocaleType).isTrue()
    }

    @DisplayName("String Type의 Locale 이름을 전달하였을때, 해당하는 Locale이 존재하지 않으면 False를 반환한다.")
    @Test
    fun isLocaleTypeFalseTest() {
        // given
        val type = "jp"

        // when
        val isLocaleType = LocaleType.isLocaleType(type)

        // then
        assertThat(isLocaleType).isFalse()
    }
}