package kr.co.ddamddam.useremail.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestControllerAdvice
public class UserEmailExceptionHandler {
    @ExceptionHandler({MessagingException.class, UnsupportedEncodingException.class})
    public ResponseEntity<?> memberEmailExceptionHandler() {
        return ResponseEntity.internalServerError().body("회원가입 인증 코드 전송에 실패했습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid
    public ResponseEntity<?> methodArgumentNotValidException() {
        return ResponseEntity.badRequest().body("회원가입 인증 코드를 받을 이메일 양식이 다릅니다.");
    }
}
