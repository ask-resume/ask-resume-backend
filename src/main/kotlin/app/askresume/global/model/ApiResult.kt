package app.askresume.global.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiResult<T>(
    val data: T,
) {

    val timestamp = LocalDateTime.now()

    @Schema(description = "status", example = "200")
    val status = HttpStatus.OK.value()
}