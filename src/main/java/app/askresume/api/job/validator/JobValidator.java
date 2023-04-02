package app.askresume.api.job.validator;

import app.askresume.domain.job.constant.LocaleType;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class JobValidator {

    public void validateLocaleType(String locale) {
        if (!LocaleType.isLocaleType(locale)) {
            throw new BusinessException(ErrorCode.INVALID_LOCALE_TYPE);
        }
    }
}
