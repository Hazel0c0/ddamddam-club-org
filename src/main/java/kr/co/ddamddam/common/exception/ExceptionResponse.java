package kr.co.ddamddam.common.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kr.co.ddamddam.common.exception.custom.CustomErrorCode;
import kr.co.ddamddam.common.exception.custom.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 우리가 만든 CustomException 을 핸들러가 캐치했을 때
 * 클라이언트에게 전달할 객체
 * @param <T> - CustomException 을 상속한 클래스 객체 (ex. qna 의 QnaException)
 *            - 에러코드와 에러메세지를 담고 있습니다.
 * @param <V> - 추가적인 내용이 필요할 때 담을 객체 (ex. Long QnaIdx)
 */
@Getter
@NoArgsConstructor
@JsonPropertyOrder({"errorCode", "message"})
public class ExceptionResponse<T extends CustomException, V> {

    private CustomErrorCode errorCode;
    private String message;
    private V payload;

    public ExceptionResponse(T customException) {
        this.errorCode = customException.getErrorCode();
        this.message = customException.getMessage();
        this.payload = null;
    }

    public ExceptionResponse(T customException, V payload) {
        this.errorCode = customException.getErrorCode();
        this.message = customException.getMessage();
        this.payload = payload;
    }
}
