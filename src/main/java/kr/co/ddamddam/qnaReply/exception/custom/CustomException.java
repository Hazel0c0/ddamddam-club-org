package kr.co.ddamddam.qnaReply.exception.custom;

import lombok.Getter;

public class CustomException extends RuntimeException {
    
    @Getter // 인터페이스를 구현하려면 @Getter 를 달아줘야 합니다.
    private final CustomErrorCode errorCode;
    
    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage()); // RuntimeException 에게 올려줄 메세지
        this.errorCode = errorCode; // 필드 업데이트
    }
}
