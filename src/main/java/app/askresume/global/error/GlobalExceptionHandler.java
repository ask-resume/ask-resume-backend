package app.askresume.global.error;

import app.askresume.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private static final String ERROR_LOG_MESSAGE = "[ERROR] {} : {}";

    // binding error가 발생할 경우
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


    // 주로 @RequestParam enum으로 binding 못했을 경우 발생
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    // 지원하지 않은 HTTP method 호출 할 경우 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }


    // 필수값이 비어 있는경우, 발생
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ErrorCode errorCode = ErrorCode.OMITTING_REQUIRED_VALUES;

        final String logMessage = messageSource.getMessage(errorCode.getProperties(), null, Locale.KOREAN);
        final String userMessage = messageSource.getMessage(errorCode.getProperties(), null, LocaleContextHolder.getLocale());

        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), logMessage, e);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getErrorCode(), userMessage);

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(errorResponse);
    }


    // 비즈니스 로직 실행 중 오류 발생
    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handBusinessException(BusinessException e) {
        final String logMessage = messageSource.getMessage(e.getMessage(), null, Locale.KOREA);
        final String userMessage = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());

        e.getMessage();
        e.getLocalizedMessage();

        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), logMessage, e);

        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getErrorCode(), userMessage);

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    // 나머지 예외 발생
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}