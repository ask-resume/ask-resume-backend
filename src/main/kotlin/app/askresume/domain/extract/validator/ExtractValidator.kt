package app.askresume.domain.extract.validator

import app.askresume.domain.extract.exception.NotPermittedContentTypeException
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
            throw NotPermittedContentTypeException()
    }
}

