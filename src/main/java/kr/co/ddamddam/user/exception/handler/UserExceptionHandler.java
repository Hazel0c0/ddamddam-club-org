package kr.co.ddamddam.user.exception.handler;

import kr.co.ddamddam.common.exception.ExceptionResponse;
import kr.co.ddamddam.common.exception.custom.LoginException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sevice 에서 던진 에러를 잡아서 처리하는 핸들러
 */

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(NotFoundUserException.class)
    public ExceptionResponse<NotFoundUserException, Long> userException(NotFoundUserException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getUserIdx());
        return new ExceptionResponse<>(e, e.getUserIdx());
    }

    @ExceptionHandler(LoginException.class)
    public ExceptionResponse<LoginException, String> userEmailException(LoginException e) {
        log.error("notFoundUserException : {} {} {}", e.getErrorCode(), e.getMessage(), e.getUserEmail());
        return new ExceptionResponse<>(e, e.getUserEmail());
    }

}
