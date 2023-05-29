package app.askresume.global.error

import org.springframework.validation.BindingResult

class ErrorResponse(
    val errorCode: String,
    val errorMessage: String? = null,
) {

    companion object {
        fun of(errorCode: String, errorMessage: String?): ErrorResponse {
            return ErrorResponse(
                errorCode = errorCode,
                errorMessage = errorMessage,
            )
        }

        fun of(errorCode: String, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(
                errorCode = errorCode,
                errorMessage = createErrorMessage(bindingResult),
            )
        }

        private fun createErrorMessage(bindingResult: BindingResult): String {
            val sb = StringBuilder()
            var isFirst = true

            val fieldErrors = bindingResult.fieldErrors
            for (fieldError in fieldErrors) {
                if (!isFirst) {
                    sb.append(", ")
                } else {
                    isFirst = false
                }
                sb.append("[")
                sb.append(fieldError.field)
                sb.append("] ")
                sb.append(fieldError.defaultMessage)
            }

            return sb.toString()
        }
    }

}