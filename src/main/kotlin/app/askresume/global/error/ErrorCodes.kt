package app.askresume.global.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(
    val errorCode: String,
    val description: String
) {

    ENUM_VALIDATE_NOT_EXIST("VAL-001", "enum validate check fail"),

    ENTITY_NOT_FOUND("ENY-001", "Entity 데이터가 존재하지 않습니다."),

    NOT_PERMITTED_CONTENT_TYPE("FILE-001", "허가되지 않는 CONTENT TYPE 입니다."),
    ;

    fun toHttpStatus(): HttpStatus = when (this) {
        ENUM_VALIDATE_NOT_EXIST,
        ENTITY_NOT_FOUND,
        NOT_PERMITTED_CONTENT_TYPE -> HttpStatus.BAD_REQUEST
    }

}