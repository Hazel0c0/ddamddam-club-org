package kr.co.ddamddam.qna.qnaReply.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListPageResponseDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna-reply")
public class QnaReplyApiController {

    private final QnaReplyService qnaReplyService;

    @GetMapping("/{boardIdx}")
    public ApplicationResponse<?> getList(
        PageDTO pageDTO,
        Long boardIdx
    ) {
        log.info("GET : /qna/{}/replies?page={}&size={} - 댓글 전체보기", boardIdx, pageDTO.getPage(), pageDTO.getSize());



//        QnaReplyListPageResponseDTO qnaReplyList = qnaReplyService.getList(pageDTO, boardIdx);

        List<QnaReplyListResponseDTO> list = qnaReplyService.getList(pageDTO, boardIdx);

        System.out.println("list = " + list);

        return ApplicationResponse.ok(list);
    }
}
