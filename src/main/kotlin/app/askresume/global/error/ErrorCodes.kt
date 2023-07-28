package app.askresume.global.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(
    val errorCode: String,
    val description: String
) {

    INTERNAL_SERVER_ERROR("SYS-001", "예상하지 못한 에러가 발생하였습니다."),
    OMITTING_REQUIRED_VALUES("SYS-002","필수값이 생략되었습니다."),

    ENUM_VALIDATE_NOT_EXIST("VAL-001", "enum validate check fail"),

    ALREADY_REGISTERED_MEMBER("MEM-001", "이미 등록된 회원입니다."),

    ENTITY_NOT_FOUND("ENY-001", "Entity 데이터가 존재하지 않습니다."),

    STATUS_IS_NOT_COMPLETED("SNC-001", "COMPLETED 되지 않은 내용에 접근 시도"),

    TOKEN_EXPIRED("AUTH-001", "토큰이 만료 되었습니다."),
    NOT_VALID_TOKEN( "AUTH-002", "토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND("AUTH-005", "Refresh Token이 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED("AUTH-006", "Refresh Token이 만료 되었습니다."),
    NOT_ACCESS_TOKEN_TYPE("AUTH-007", "Access Token이 아닙니다."),
    FORBIDDEN_ADMIN("AUTH-008", "관리자권한이 필요합니다."),

    NOT_PERMITTED_CONTENT_TYPE("FILE-001", "허가되지 않는 CONTENT TYPE 입니다."),
    ;

    fun toHttpStatus(): HttpStatus = when (this) {
        INTERNAL_SERVER_ERROR ->HttpStatus.INTERNAL_SERVER_ERROR

        REFRESH_TOKEN_NOT_FOUND,
        REFRESH_TOKEN_EXPIRED,
        TOKEN_EXPIRED,
        NOT_ACCESS_TOKEN_TYPE,
        NOT_VALID_TOKEN -> HttpStatus.UNAUTHORIZED

        FORBIDDEN_ADMIN -> HttpStatus.FORBIDDEN

        OMITTING_REQUIRED_VALUES,
        ALREADY_REGISTERED_MEMBER,
        ENUM_VALIDATE_NOT_EXIST,
        ENTITY_NOT_FOUND,
        STATUS_IS_NOT_COMPLETED,
        NOT_PERMITTED_CONTENT_TYPE -> HttpStatus.BAD_REQUEST
    }

}