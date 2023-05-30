package app.askresume.domain.member.exception

import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException

class DuplicateMemberException : BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER)

