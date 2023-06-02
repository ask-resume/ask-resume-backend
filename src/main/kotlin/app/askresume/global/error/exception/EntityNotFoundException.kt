package app.askresume.global.error.exception

import app.askresume.global.error.ErrorCode

class EntityNotFoundException(errorCode: ErrorCode) : BusinessException(errorCode)