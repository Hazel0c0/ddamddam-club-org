package kr.co.ddamddam.qna.qnaReply.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.qna.qnaReply.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna-reply")
public class QnaReplyApiController {

    private final QnaReplyService qnaReplyService;

    @GetMapping
    public ApplicationResponse<?> getList(

    ) {
        return null;
    }
}
