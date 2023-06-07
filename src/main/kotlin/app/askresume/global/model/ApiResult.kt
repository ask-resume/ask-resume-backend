package app.askresume.global.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiResult<T>(
    val data: T,
) {

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val timestamp: LocalDateTime = LocalDateTime.now()

    @Schema(description = "status", example = "200")
    val status = HttpStatus.OK.value()
}