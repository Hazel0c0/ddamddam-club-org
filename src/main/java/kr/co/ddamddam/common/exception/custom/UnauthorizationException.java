package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class UnauthorizationException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String str;

    public UnauthorizationException(ErrorCode e, String str) {
        super(e);
        this.errorCode = e;
        this.str = str;
    }
}
