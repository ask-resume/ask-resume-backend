package app.askresume.global.error

import app.askresume.global.util.LoggerUtil.logger
import feign.FeignException
import feign.Response
import feign.RetryableException
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus

class FeignClientExceptionErrorDecoder(
    private val errorDecoder: ErrorDecoder = ErrorDecoder.Default(),
) : ErrorDecoder {

    private val log = logger()

    override fun decode(methodKey: String, response: Response): Exception {
        log.error("{} 요청 실패, status : {}, reason : {}", methodKey, response.status(), response.reason())

        val exception = FeignException.errorStatus(methodKey, response)
        val httpStatus = HttpStatus.valueOf(response.status())

        return if (httpStatus.is5xxServerError) {
            RetryableException(
                response.status(),
                exception.message,
                response.request().httpMethod(),
                exception,
                null,
                response.request()
            )
        } else errorDecoder.decode(methodKey, response)
    }
}
