package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class NotFoundUserException extends CustomException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Long userIdx;

    public NotFoundUserException(ErrorCode e, Long userIdx) {
        super(e);
        this.errorCode = e;
        this.userIdx = userIdx;
    }
}
