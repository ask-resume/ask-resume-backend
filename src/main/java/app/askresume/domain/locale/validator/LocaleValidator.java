package app.askresume.domain.locale.validator;

import app.askresume.domain.locale.constant.LocaleType;
import org.springframework.stereotype.Service;

@Service
public class LocaleValidator {

    public String validateLocaleType(String locale) {
        return (!LocaleType.isLocaleType(locale)) ? LocaleType.EN.name() : locale;
    }
}
