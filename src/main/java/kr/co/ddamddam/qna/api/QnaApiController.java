package kr.co.ddamddam.qna.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.response.QnaListResponseDTO;
import kr.co.ddamddam.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna")
public class QnaApiController {

    private final QnaService qnaService;

    // QNA 페이지 전체 목록 조회
    @GetMapping
    public ApplicationResponse<QnaListResponseDTO> getList(PageDTO pageDTO) {

        log.info("/api/qna/page/{}/size/{}", pageDTO.getPage(), pageDTO.getSize());

        QnaListResponseDTO list = qnaService.getList(pageDTO);

        return ApplicationResponse.ok(list);
    }
}
