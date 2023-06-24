package app.askresume.global.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(
    val errorCode: String,
    val description: String
) {

    ENUM_VALIDATE_NOT_EXIST("VAL-001", "enum validate check fail") ,

    ENTITY_NOT_FOUND("ENY-001", "Entity 데이터가 존재하지 않습니다."),
    ;

    fun toHttpStatus(): HttpStatus = when (this) {
        ENUM_VALIDATE_NOT_EXIST,ENTITY_NOT_FOUND -> HttpStatus.BAD_REQUEST
    }

}