package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class NotFoundUserByEmailException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String userEmail;

    public NotFoundUserByEmailException(ErrorCode e, String userEmail) {
        super(e);
        this.errorCode = e;
        this.userEmail = userEmail;
    }
}
