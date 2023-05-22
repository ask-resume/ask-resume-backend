package app.askresume.domain.prompt.repository;

import app.askresume.domain.prompt.constant.PromptType;
import app.askresume.domain.prompt.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptRepository extends JpaRepository<Prompt, Long> {

    Optional<Prompt> findByPromptType(PromptType promptType);
}
