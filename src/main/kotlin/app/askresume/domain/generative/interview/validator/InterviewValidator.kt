package app.askresume.domain.generative.interview.validator

import app.askresume.domain.generative.interview.exception.DifficultyNotExistsException
import app.askresume.domain.resume.constant.DifficultyType
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class InterviewValidator {

    fun validateDifficultyType(difficulty: String) {
        if (!DifficultyType.isDifficultyType(difficulty)) {
            throw DifficultyNotExistsException()
        }
    }
}
