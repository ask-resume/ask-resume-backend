package app.askresume.api.resume.validator;

import app.askresume.domain.gpt.constant.DifficultyType;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeValidator {

    @Value("${file.licensed.content}")
    private List<String> licensedContentTypes;

    public void validateDifficultyType(String type) {
        if (!DifficultyType.isDifficultyType(type)) {
            throw new BusinessException(ErrorCode.INVALID_DIFFICULTY_TYPE);
        }
    }
}
