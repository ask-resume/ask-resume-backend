package app.askresume.global.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(
    val errorCode: String,
    val description: String
) {

    ENTITY_NOT_FOUND("ENY-001", "entity 데이터가 존재하지 않습니다."),
    ;

    fun toHttpStatus(): HttpStatus = when (this) {
        ENTITY_NOT_FOUND -> HttpStatus.BAD_REQUEST
    }

}