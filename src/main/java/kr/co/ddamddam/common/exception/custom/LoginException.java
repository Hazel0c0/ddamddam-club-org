package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class LoginException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String str;

    public LoginException(ErrorCode e, String str) {
        super(e);
        this.errorCode = e;
        this.str = str;
    }
}
