package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;

public class NotFoundReplyException extends CustomException {

    @Getter
    private final ErrorCode errorCode; // 상태코드와 메세지

    @Getter
    private final Long idx;

    public NotFoundReplyException(ErrorCode e, Long idx) {
        super(e);
        this.errorCode = e;
        this.idx = idx;
    }
}
