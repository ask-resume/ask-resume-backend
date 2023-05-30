package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCode

class AuthenticationException(errorCode: ErrorCode) : BusinessException(errorCode)