package kr.co.ddamddam.qna.qnaReply.exception.custom;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {

    HttpStatus getHttpStatus(); // 상태코드 HttpStatus Getter
    String getMessage(); // 메시지 String Getter
}
