package app.askresume.api.member.validator

import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException

object PasswordValidator {
    fun passwordCheck(password1: String, password2: String) {
        if (password1 != password2)
            throw BusinessException(ErrorCode.SIGNUP_PASSWORD_INCONSISTENCY)
    }

}

