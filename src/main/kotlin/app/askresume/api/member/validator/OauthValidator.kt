package app.askresume.api.member.validator

import app.askresume.domain.member.constant.MemberType
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class OauthValidator {

    fun validateMemberType(memberType: String) {
        if (!MemberType.isMemberType(memberType)) {
            throw BusinessException(ErrorCode.INVALID_MEMBER_TYPE)
        }
    }

}

