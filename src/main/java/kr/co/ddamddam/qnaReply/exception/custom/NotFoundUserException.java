package kr.co.ddamddam.qnaReply.exception.custom;

import lombok.Getter;

public class NotFoundUserException extends CustomException {

    @Getter
    private final QnaReplyErrorCode errorCode;

    @Getter
    private final Long userIdx;

    public NotFoundUserException(QnaReplyErrorCode e, Long idx) {
        super(e);
        this.errorCode = e;
        this.userIdx = idx;
    }
}
