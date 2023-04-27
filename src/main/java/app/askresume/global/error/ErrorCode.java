package app.askresume.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /** 인증 & 권한 부여 */
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-001", "token.expired"),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-002", "not.valid.token"),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "AUTH-003", "not.exists.authorization"),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "AUTH-004", "not.valid.bearer.grant.type"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-005", "refresh.token.not.found"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-006", "refresh.token.expired"),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "AUTH-007", "not.access.token.type"),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "AUTH-008", "forbidden.admin"),

    /** 사용자 */
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "MEM-001", "invalid.member.type"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "MEM-002", "already.registered.member"),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "MEM-003", "member.not.exists"),
    EMAIL_OR_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "MEM-004", "email.or.password.inconsistency"),
    SIGNUP_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "MEM-005", "signup.password.inconsistency"),

    /** 이력서 */
    INVALID_DIFFICULTY_TYPE(HttpStatus.BAD_REQUEST, "RES-001", "invalid.difficulty.type"),

    /** 파일 입출력 */
    NOT_PERMITTED_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "FILE-001", "not.permitted.content.type"),
    TEXT_EXTRACTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-002", "text.extraction.failed"),

    /** 직업 */
    JOB_NOT_EXISTS(HttpStatus.BAD_REQUEST, "JOB-001", "job.not.exists"),

    /** 시스템 에러 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-001", "internal.server.error"),
    THREAD_MALFUNCTION(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-002", "thread.malfunction"),
    JSON_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-003", "json.parsing.failed"),
    OMITTING_REQUIRED_VALUES(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-004", "omitting.required.values");

    ErrorCode(HttpStatus httpStatus, String errorCode, String properties) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.properties = properties;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String properties;

}
