package app.askresume.domain.prompt.service;

import app.askresume.domain.prompt.constant.PromptType;
import app.askresume.domain.prompt.repository.PromptRepository;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    @Cacheable(cacheNames = "promptCache", key = "#promptType.toString()")
    public String findByPromptType(PromptType promptType) {
        return promptRepository.findByPromptType(promptType)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROMPT_NOT_EXISTS))
                .getContent();
    }

}
