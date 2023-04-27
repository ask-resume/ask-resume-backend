package app.askresume.api.member.validator;

import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;

public class PasswordValidator {

    public static void passwordCheck(String password1, String password2) {
        if(!password1.equals(password2))
            throw new BusinessException(ErrorCode.SIGNUP_PASSWORD_INCONSISTENCY);
    }
}
