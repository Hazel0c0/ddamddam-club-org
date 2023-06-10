package kr.co.ddamddam.qnaReply.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * QNA 에서 발생할 수 있는 에러 내용을 관리하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum QnaReplyErrorCode implements CustomErrorCode {
    
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "❌ 회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "❌ 게시글을 찾을 수 없습니다."),
    FAIL_WRITE_BOARD(HttpStatus.BAD_REQUEST, "❌ 게시글 작성에 실패했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "❌ 파라미터 요청이 잘못 되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
