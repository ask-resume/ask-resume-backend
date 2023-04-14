package app.askresume.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /**
     * 인증 & 권한 부여
     */

    // 토큰이 만료되었습니다.
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-001", "The token has expired."),

    // 토큰이 유효하지 않습니다.
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-002", "The token is not valid."),

    // Authorization Header가 빈 값입니다.
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "AUTH-003", "The Authorization Header is empty."),

    // 잘못된 인증 타입입니다.
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "AUTH-004", "The authentication type is incorrect."),

    // 해당 refresh token이 존재하지 않습니다.
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-005", "The specified refresh token does not exist."),

    // 해당 refresh token이 만료되었습니다.
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-006", "The specified refresh token has expired."),

    // ACCESS TOKEN이 아닌 다른 타입의 토큰입니다.
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "AUTH-007", "The token type is not an access token."),

    // 관리자 권한이 필요합니다.
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "AUTH-008", "Administrator privileges are required."),

    /**
     * 사용자
     */

    // 잘못된 멤버 형식입니다. (memberType: GOOGLE).
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "MEM-001", "Invalid member type (memberType: GOOGLE)."),

    // 멤버가 이미 등록되어 있습니다.
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "MEM-002", "The member is already registered."),

    // 지정한 멤버가 없습니다.
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "MEM-003", "The specified member does not exist."),

    // 이메일 또는 비밀번호가 올바르지 않습니다.
    EMAIL_OR_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "MEM-004", "The email or password is incorrect."),

    // 암호 및 암호 확인 값이 일치하지 않습니다.
    SIGNUP_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "MEM-005", "The password and password confirmation values do not match."),

    /**
     * 이력서
     */

    // 난이도가 잘못되었습니다.
    INVALID_DIFFICULTY_TYPE(HttpStatus.BAD_REQUEST, "RES-001", "Invalid difficulty type."),

    /**
     * 파일 입출력
     */

    // 지정된 CONTENT TYPE이 허용되지 않습니다.
    NOT_PERMITTED_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "FILE-001", "The specified CONTENT TYPE is not permitted."),

    // 텍스트를 추출하는 동안 오류가 발생했습니다.
    TEXT_EXTRACTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-002", "An error occurred during text extraction."),

    /**
     * Locale
     */

    // Locale 정보가 잘못되었습니다.
    INVALID_LOCALE_TYPE(HttpStatus.BAD_REQUEST, "LOC-001", "Invalid locale information."),

    /**
     * 직업
     */

    // 지정한 작업이 없습니다.
    JOB_NOT_EXISTS(HttpStatus.BAD_REQUEST, "JOB-001", "The specified job does not exist."),

    /**
     * 시스템 에러
     */

    // 스레드 작업 중에 문제점이 발생했습니다.
    THREAD_MALFUNCTION(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-001", "A problem occurred during thread operation."),

    // JSON 구문 분석 중에 오류가 발생했습니다.
    JSON_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-002", "An error occurred during JSON parsing.");

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
