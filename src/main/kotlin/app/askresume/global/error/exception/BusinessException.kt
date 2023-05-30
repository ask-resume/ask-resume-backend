package app.askresume.global.error.exception;

import app.askresume.global.error.ErrorCode

open class BusinessException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.properties)