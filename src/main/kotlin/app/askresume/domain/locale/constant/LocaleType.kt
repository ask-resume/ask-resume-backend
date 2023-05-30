package app.askresume.domain.locale.constant

import java.util.*

enum class LocaleType(private val value: String) {

    KO("Korean"), EN("English");

    fun value() = value

    companion object {
        fun from(type: String): LocaleType {
            return valueOf(type.uppercase(Locale.getDefault()))
        }

        fun isLocaleType(type: String): Boolean {
            val localeTypes = Arrays.stream(values())
                .filter { localeType -> localeType.value.equals(type, ignoreCase = true) }.toList()
            return localeTypes.size != 0
        }
    }
}

