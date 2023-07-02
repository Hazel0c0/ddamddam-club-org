package kr.co.ddamddam.common.exception.handler;

import kr.co.ddamddam.common.exception.ExceptionResponse;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.NotFoundReplyException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sevice 또는 Controller 에서 던진 에러를 잡아서 처리하는 핸들러
 */

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundBoardException.class)
    public ResponseEntity<?> boardException(NotFoundBoardException e) {
        // 에러 코드, 에러 메세지, 에러가 발생한 QNA 게시글 번호
        log.error("notFoundQnaBoardException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getIdx());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundReplyException.class)
    public ResponseEntity<?> replyException(NotFoundReplyException e) {
        // 에러 코드, 에러 메세지, 에러가 발생한 QNA 게시글 번호
        log.error("notFoundQnaBoardException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getIdx());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> userException(NotFoundUserException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getUserIdx());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizationException.class)
    public ResponseEntity<?> userException(UnauthorizationException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getStr());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

}
