package kr.co.ddamddam.qna.qnaReply.exception.handler;

import kr.co.ddamddam.common.exception.ExceptionResponse;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sevice 에서 던진 에러를 잡아서 처리하는 핸들러
 */

@Slf4j
@RestControllerAdvice
public class QnaReplyExceptionHandler {

    @ExceptionHandler({NotFoundQnaBoardException.class, RuntimeException.class})
    public ExceptionResponse<NotFoundQnaBoardException, Long> qnaBoardException(NotFoundQnaBoardException e) {
        // 에러 코드, 에러 메세지, 에러가 발생한 QNA 게시글 번호
        log.error("notFoundQnaBoardException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getIdx());
        return new ExceptionResponse<>(e, e.getIdx());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ExceptionResponse<NotFoundUserException, Long> QnaUserException(NotFoundUserException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getUserIdx());
        return new ExceptionResponse<>(e, e.getUserIdx());
    }

}
