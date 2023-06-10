package kr.co.ddamddam.qna.qnaReply.exception.custom;

import lombok.Getter;

public class NotFoundQnaBoardException extends CustomException {

    @Getter
    private final QnaReplyErrorCode errorCode; // 상태코드와 메세지

    @Getter
    private final Long idx;

    /**
     * CustomException 의 필드값이 CustomErrorCode 인데,
     * QnaException 이 CustomErrorCode 를 implements 하기 때문에
     * CustomException 에 QnaException 을 매개변수로 전달할 수 있다.
     * @param e - 커스텀한 에러 코드
     * @param idx - 에러가 발생한 게시판 or 유저 or 댓글의 idx
     */
    public NotFoundQnaBoardException(QnaReplyErrorCode e, Long idx) {
        super(e); 
        this.errorCode = e;
        this.idx = idx;
    }
}
