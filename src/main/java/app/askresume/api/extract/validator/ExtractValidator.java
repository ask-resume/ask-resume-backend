package app.askresume.api.extract.validator;

import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractValidator {

    @Value("${file.licensed.content}")
    private List<String> licensedContentTypes;

    public void validateContentType(String contentType) {
        List<String> contentTypes = licensedContentTypes.stream()
                .filter(type -> !type.equalsIgnoreCase(contentType)).toList();

        if (contentTypes.size() != 0)
            throw new BusinessException(ErrorCode.NOT_PERMITTED_CONTENT_TYPE);

    }
}
