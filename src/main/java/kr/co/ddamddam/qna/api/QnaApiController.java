package kr.co.ddamddam.qna.api;

import kr.co.ddamddam.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class QnaApiController {

    private final QnaService qnaService;
}
