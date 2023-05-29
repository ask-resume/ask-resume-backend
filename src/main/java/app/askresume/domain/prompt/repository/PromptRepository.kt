package app.askresume.domain.prompt.repository

import app.askresume.domain.prompt.constant.PromptType
import app.askresume.domain.prompt.model.Prompt
import org.springframework.data.jpa.repository.JpaRepository


interface PromptRepository : JpaRepository<Prompt, Long> {

    fun findByPromptType(promptType: PromptType): Prompt?
}

