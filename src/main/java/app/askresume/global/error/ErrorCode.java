package app.askresume.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입 입니다.(memberType : GOOGLE)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),

    EMAIL_OR_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "M-004", "이메일 또는 비밀번호가 일치하지 않습니다."),
    SIGNUP_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "M-005", "비밀번호와, 비밀번호체크가 일치하지 않습니다."),

    // 이력서 관련
    INVALID_DIFFICULTY_TYPE(HttpStatus.BAD_REQUEST, "R-001", "잘못된 난이도 입니다."),


    // 파일 입출력
    NOT_PERMITTED_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "F-001", "허가된 CONTENT_TYPE이 아닙니다."),
    TEXT_EXTRACTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F-002", "TEXT를 읽는 도중 에러가 발생하였습니다."),

    // 로케일 관련
    INVALID_LOCALE_TYPE(HttpStatus.BAD_REQUEST, "L-001", "잘못된 로케일 정보입니다."),

    // 직업 관련
    JOB_NOT_EXISTS(HttpStatus.BAD_REQUEST, "J-001", "해당 직업은 존재하지 않습니다.")
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

}
