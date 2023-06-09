package kr.co.ddamddam.qna.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QnaErrorCode implements CustomErrorCode {

    // QNA 에서 에러 발생시 리턴할 내용을 열거
    NOT_FOUND_PAGE(HttpStatus.NOT_FOUND, "QNA 페이지 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
