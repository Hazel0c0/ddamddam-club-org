package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class LoginException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String userEmail;

    public LoginException(ErrorCode e, String userEmail) {
        super(e);
        this.errorCode = e;
        this.userEmail = userEmail;
    }
}
