package app.askresume.global.error

import app.askresume.global.error.exception.BusinessException
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource,
) {

    private val log = logger()

    // binding error가 발생할 경우
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, e.message, e)

        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.bindingResult)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    // RequestBody에 enum에 데이터 바인딩이 안되는 경우 발생
    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorBook = ErrorCodes.INVALID_REQUEST_BODY
        val message = messageSource.getMessage("invalid.request.body", null, LocaleContextHolder.getLocale())

        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, message, e)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse.of(
                    errorBook.errorCode,
                    message,
                )
            )
    }

    // 지원하지 않은 HTTP method 호출 할 경우 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        val errorBook = ErrorCodes.METHOD_NOT_ALLOWED

        val message = messageSource.getMessage("method.not.allowed", arrayOf(e.method), LocaleContextHolder.getLocale())
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, message, e)

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(
                ErrorResponse.of(
                    errorBook.errorCode,
                    message,
                )
            )
    }


    // 필수값이 비어 있는경우, 발생
    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        val errorBook = ErrorCodes.OMITTING_REQUIRED_VALUES

        val message = messageSource.getMessage(
            "omitting.required.values",
            arrayOf(e.parameterName, e.parameterType),
            LocaleContextHolder.getLocale()
        )

        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, message, e)

        return ResponseEntity.status(errorBook.toHttpStatus())
            .body(
                ErrorResponse.of(
                    errorBook.errorCode,
                    message,
                )
            )
    }

    // (신규) 비즈니스 로직 실행 중 오류 발생
    @ExceptionHandler(value = [BusinessException::class])
    protected fun handBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        val message = messageSource.getMessage(e.properties, e.arguments, LocaleContextHolder.getLocale())
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, message, e)

        return ResponseEntity.status(e.codeBook.toHttpStatus())
            .body(
                ErrorResponse.of(
                    e.codeBook.errorCode,
                    message,
                )
            )
    }

    // 나머지 예외 발생
    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val errorBook = ErrorCodes.INTERNAL_SERVER_ERROR

        val message = messageSource.getMessage("internal.server.error", null, LocaleContextHolder.getLocale())
        log.error(ERROR_LOG_MESSAGE, e.javaClass.simpleName, message, e)

        return ResponseEntity.status(errorBook.toHttpStatus())
            .body(
                ErrorResponse.of(
                    errorBook.errorCode,
                    message,
                )
            )
    }

    companion object {
        private const val ERROR_LOG_MESSAGE = "[ERROR] {} : {}"
    }
}