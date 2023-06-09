package kr.co.ddamddam.qna.exception.handler;

import kr.co.ddamddam.common.exception.ExceptionResponse;
import kr.co.ddamddam.qna.exception.custom.CustomException;
import kr.co.ddamddam.qna.exception.custom.QnaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class QnaExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse<QnaException, Long> notFoundQnaException(QnaException e) {
        // 에러 코드, 에러 메세지, 에러가 발생한 QNA 게시글 번호
        log.error("QnaExceptionHandler : {} {} {}", e.getErrorCode(), e.getMessage(), e.getQnaIdx());
        return new ExceptionResponse<>(e, e.getQnaIdx());
    }
}
