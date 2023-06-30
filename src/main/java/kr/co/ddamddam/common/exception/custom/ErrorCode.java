package kr.co.ddamddam.common.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * QNA 에서 발생할 수 있는 에러 내용을 관리하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode implements CustomErrorCode {
    
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "❌ 파라미터 요청이 잘못 되었습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "❌ 회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "❌ 게시글을 찾을 수 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "❌ 댓글을 찾을 수 없습니다."),
    NOT_FOUND_USER_BY_EMAIL(HttpStatus.NOT_FOUND, "❌ 입력한 이메일의 회원을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "❌ 비밀번호가 틀렸습니다."),
    UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "❌ 로그인 하지 않은 사용자이거나, 위조된 토큰입니다."),
    MESSAGE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "❌ 메일 발송에 실패했습니다."),
    ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "❌ 로그인 되었으나 접근 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
