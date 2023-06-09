package kr.co.ddamddam.qna.exception.custom;

import lombok.Getter;

public class FailDeleteBoardException extends CustomException {

    @Getter
    private final QnaErrorCode errorCode;

    @Getter
    private final Long boardIdx;

    public FailDeleteBoardException(QnaErrorCode e, Long idx) {
        super(e);
        this.errorCode = e;
        this.boardIdx = idx;
    }
}
