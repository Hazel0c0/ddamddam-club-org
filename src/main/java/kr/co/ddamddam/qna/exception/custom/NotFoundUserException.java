package kr.co.ddamddam.qna.exception.custom;

import lombok.Getter;

public class NotFoundUserException extends CustomException {

    @Getter
    private final QnaErrorCode errorCode;

    @Getter
    private final Long userIdx;

    public NotFoundUserException(QnaErrorCode e, Long idx) {
        super(e);
        this.errorCode = e;
        this.userIdx = idx;
    }
}
