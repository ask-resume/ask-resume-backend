package app.askresume.domain.locale.validator

import app.askresume.domain.locale.constant.LocaleType
import org.springframework.stereotype.Service

@Service
class LocaleValidator {

    fun validateLocaleType(locale: String): String {
        return if (!LocaleType.isLocaleType(locale))
            LocaleType.EN.name
        else
            locale
    }
}

