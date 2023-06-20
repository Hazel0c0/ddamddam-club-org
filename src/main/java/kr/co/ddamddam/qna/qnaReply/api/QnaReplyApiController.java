package kr.co.ddamddam.qna.qnaReply.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.request.QnaReplyInsertRequestDTO;
import kr.co.ddamddam.qna.qnaReply.dto.request.QnaReplyModifyRequestDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListPageResponseDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.ddamddam.common.response.ResponseMessage.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna-reply")
public class QnaReplyApiController {

    private final QnaReplyService qnaReplyService;

    /**
     * QNA 댓글 전체보기
     * [GET] /api/ddamddam/qna-reply/{boardIdx}
     * @param boardIdx - 댓글 목록을 확인할 게시글의 index
     * @return - 요청받은 게시글의 댓글 리스트 (과거순)
     */
    @GetMapping("/{boardIdx}")
    public ApplicationResponse<?> getList(
        @PathVariable Long boardIdx
    ) {
        log.info("GET : /qna-reply/{} - 댓글 전체보기", boardIdx);

        List<QnaReplyListResponseDTO> list = qnaReplyService.getList(boardIdx);

        return ApplicationResponse.ok(list);
    }

    /**
     * QNA 댓글 생성 (작성)
     * [POST] /api/ddamddam/qna-reply/write
     * ❗ 채택 완료된 게시글에는 댓글 작성이 불가능 합니다.
     * @param dto - 댓글을 작성할 게시글의 index, 작성한 댓글 내용
     * @return - 작성 성공시 게시글의 index, 작성 실패시 FAIL
     */
    @PostMapping("/write")
    public ApplicationResponse<?> writeReply(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody QnaReplyInsertRequestDTO dto
    ) {
        log.info("POST : /qna-reply/write - QNA {}번 게시글에 '{}' 댓글 작성", dto.getBoardIdx(), dto.getReplyContent());

        ResponseMessage result = qnaReplyService.writeReply(tokenUserInfo, dto);

        if (result == FAIL) {
            return ApplicationResponse.bad(result);
        }

        List<QnaReplyListResponseDTO> list = qnaReplyService.getList(dto.getBoardIdx());

        return ApplicationResponse.ok(list);
    }

    /**
     * QNA 댓글 삭제
     * ❗ 채택이 완료된 댓글은 삭제가 불가능합니다.
     * [DELETE] /api/ddamddam/delete/{replyIdx}
     * @param replyIdx - 삭제할 댓글의 index
     * @return 삭제 성공시 SUCCESS, 삭제 실패시 FAIL
     */
    @DeleteMapping("/delete/{replyIdx}")
    public ApplicationResponse<?> deleteReply(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @PathVariable Long replyIdx
    ) {
        log.info("DELETE : /qna-reply/delete/{} - QNA 댓글 삭제", replyIdx);

        ResponseMessage result = qnaReplyService.deleteReply(tokenUserInfo, replyIdx);

        if (result == FAIL) {
            return ApplicationResponse.bad(result);
        }

        return ApplicationResponse.ok(result);
    }

    /**
     * QNA 댓글 수정
     * ❗ 채택이 완료된 게시글의 댓글은 수정이 불가능합니다.
     * [PATCH] /api/ddamddam/modify
     * @param dto - 댓글 index, 수정한 댓글 내용
     * @return - 수정 성공시 SUCCESS, 수정 실패시 FAIL
     */
    @PatchMapping("/modify")
    public ApplicationResponse<?> modifyReply(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody QnaReplyModifyRequestDTO dto
    ) {
        log.info("PATCH : /qna-reply/modify/{} - QNA 댓글 수정", dto.getReplyIdx());

        ResponseMessage result = qnaReplyService.modifyReply(tokenUserInfo, dto);

        if (result == FAIL) {
            ApplicationResponse.bad(result);
        }

        return ApplicationResponse.ok(result);
    }

    /**
     * QNA 댓글 채택
     * ❗ 이미 채택 완료된 게시글에서는 댓글 채택 처리가 불가능합니다. (취소도 불가능)
     * ❗ 댓글 채택 시, 해당 댓글이 작성된 게시글도 채택 완료 처리가 됩니다.
     * @param replyIdx - 채택 처리를 할 댓글의 index
     * @return - 채택 성공시 SUCCESS, 채택 실패시 FAIL
     */
    @PatchMapping("/adopts/{replyIdx}")
    public ApplicationResponse<?> adoptQnaReply(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @PathVariable Long replyIdx
    ) {
        log.info("PATCH : /qna-reply/adopts/{} - QNA 댓글 채택", replyIdx);

        ResponseMessage result = qnaReplyService.adoptQnaReply(tokenUserInfo, replyIdx);

        if (result == FAIL) {
            ApplicationResponse.bad(result);
        }

        return ApplicationResponse.ok(result);
    }
}
