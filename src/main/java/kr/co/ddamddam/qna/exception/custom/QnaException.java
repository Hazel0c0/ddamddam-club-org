package kr.co.ddamddam.qna.exception.custom;

import lombok.Getter;

public class QnaException extends CustomException {

    @Getter
    private final QnaErrorCode errorCode; // 상태코드와 메세지

    @Getter
    private final Long QnaIdx; // QNA 게시판 인덱스

    public QnaException(QnaErrorCode e, Long idx) {
        super(e); // CustomException 의 필드값이 CustomErrorCode!
                    // QnaException 이 CustomErrorCode 를 implements 하기 때문에
                    // CustomException 에 QnaException 을 매개변수로 전달할 수 있다.
        this.errorCode = e;
        this.QnaIdx = idx;
    }
}
