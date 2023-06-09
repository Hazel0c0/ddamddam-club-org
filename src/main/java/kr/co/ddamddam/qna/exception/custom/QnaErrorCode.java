package kr.co.ddamddam.qna.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * QNA 에서 발생할 수 있는 에러 내용을 관리하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum QnaErrorCode implements CustomErrorCode {
    
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "❌ 존재하지 않는 회원입니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "❌ 존재하지 않는 게시글입니다."),
    FAIL_WRITE_BOARD(HttpStatus.BAD_REQUEST, "❌ 게시글 작성에 실패했습니다."),
    INVALID_BOARD_PARAMETER(HttpStatus.BAD_REQUEST, "❌ 게시글 파라미터 값이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
