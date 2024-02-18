package app.askresume.api

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ApiResult<T>(
    val data: T,
) {

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val timestamp: LocalDateTime = LocalDateTime.now()

}