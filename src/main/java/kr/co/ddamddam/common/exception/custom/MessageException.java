package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class MessageException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String str;

    public MessageException(ErrorCode e, String str) {
        super(e);
        this.errorCode = e;
        this.str = str;
    }
}
