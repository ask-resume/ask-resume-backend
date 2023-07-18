package app.askresume.domain.generative.interview.validator

import app.askresume.domain.generative.interview.constant.DifficultyType
import app.askresume.domain.generative.interview.exception.DifficultyNotExistsException
import org.springframework.stereotype.Component

@Component
class InterviewValidator {

    fun validateDifficultyType(difficulty: String) {
        if (!DifficultyType.isDifficultyType(difficulty)) {
            throw DifficultyNotExistsException()
        }
    }
}
