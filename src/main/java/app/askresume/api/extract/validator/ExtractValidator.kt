package app.askresume.api.extract.validator

import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ExtractValidator {

    @Value("\${file.licensed.content}")
    private lateinit var licensedContentTypes: List<String>

    fun validateContentType(contentType: String?) {
        val contentTypes = licensedContentTypes.stream()
            .filter { type -> !type.equals(contentType, ignoreCase = true) }.toList()

        if (contentTypes.size != 0)
            throw BusinessException(ErrorCode.NOT_PERMITTED_CONTENT_TYPE)
    }
}

