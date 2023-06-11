package kr.co.ddamddam.qna.qnaBoard.exception.custom;

import lombok.Getter;

public class NotFoundQnaReplyException extends CustomException {

    @Getter
    private final QnaErrorCode errorCode; // 상태코드와 메세지

    @Getter
    private final Long idx;

    public NotFoundQnaReplyException(QnaErrorCode e, Long idx) {
        super(e);
        this.errorCode = e;
        this.idx = idx;
    }
}
