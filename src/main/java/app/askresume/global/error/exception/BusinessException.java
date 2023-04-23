package app.askresume.global.error.exception;

import app.askresume.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getProperties());
        this.errorCode = errorCode;
    }

}