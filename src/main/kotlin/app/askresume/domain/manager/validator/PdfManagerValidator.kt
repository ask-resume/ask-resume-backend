package app.askresume.domain.manager.validator

import app.askresume.domain.manager.exception.NotPermittedContentTypeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PdfManagerValidator {

    @Value("\${file.licensed.content}")
    private lateinit var licensedContentTypes: List<String>

    fun validateContentType(contentType: String?) {
        val contentTypes = licensedContentTypes.stream()
            .filter { type -> !type.equals(contentType, ignoreCase = true) }.toList()

        if (contentTypes.size != 0)
            throw NotPermittedContentTypeException()
    }
}

