package kr.co.ddamddam.qna.qnaReply.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListPageResponseDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//        QnaReplyListPageResponseDTO qnaReplyList = qnaReplyService.getList(pageDTO, boardIdx);

        List<QnaReplyListResponseDTO> list = qnaReplyService.getList(boardIdx);

        System.out.println("list = " + list);

        return ApplicationResponse.ok(list);
    }
//
//    @PostMapping("/write")
//    public ApplicationResponse<?> writeReply(
//            @RequestBody QnaReplyInsertRequestDTO dto
//    ) {
//        log.info("POST : /qna-reply/write - 댓글 작성");
//    }
}
