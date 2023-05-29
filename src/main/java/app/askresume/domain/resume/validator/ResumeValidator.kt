package app.askresume.domain.resume.validator

import app.askresume.domain.resume.constant.DifficultyType
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class ResumeValidator {
    fun validateDifficultyType(type: String) {
        if (!DifficultyType.isDifficultyType(type)) {
            throw BusinessException(ErrorCode.INVALID_DIFFICULTY_TYPE)
        }
    }
}
