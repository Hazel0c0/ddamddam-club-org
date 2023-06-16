package kr.co.ddamddam.user.exception;

public class NoRegisteredArgumentsException extends RuntimeException {

    // 기본 생성자 + 에러메세지르 받는 생성자
    public NoRegisteredArgumentsException(String message) {
        super(message);
    }


}
