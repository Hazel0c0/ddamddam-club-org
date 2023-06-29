package kr.co.ddamddam.user.exception.handler;

import kr.co.ddamddam.common.exception.ExceptionResponse;
import kr.co.ddamddam.common.exception.custom.LoginException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sevice 에서 던진 에러를 잡아서 처리하는 핸들러
 */

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> userException(NotFoundUserException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getUserIdx());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> userEmailException(LoginException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getStr());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(e.getMessage());
    }

}
