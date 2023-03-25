package app.askresume.domain.member.exception;


import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;

public class DuplicateMemberException extends BusinessException {

    public DuplicateMemberException() {
        super(ErrorCode.ALREADY_REGISTERED_MEMBER);
    }
}
