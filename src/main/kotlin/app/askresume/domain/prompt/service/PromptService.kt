package app.askresume.domain.prompt.service

import app.askresume.domain.prompt.constant.PromptType
import app.askresume.domain.prompt.repository.PromptRepository
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.EntityNotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class PromptService(
    private val promptRepository: PromptRepository,
) {

    @Cacheable(cacheNames = ["promptCache"], key = "#promptType.toString()")
    fun findByPromptType(promptType: PromptType): String {
        return promptRepository.findByPromptType(promptType)?.content
            ?: throw EntityNotFoundException(ErrorCode.PROMPT_NOT_EXISTS)
    }
}

