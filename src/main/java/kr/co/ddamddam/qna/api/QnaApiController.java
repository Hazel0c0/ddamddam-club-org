package kr.co.ddamddam.qna.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna")
public class QnaApiController {

    private final QnaService qnaService;

    /**
     * [GET] /api/ddamddam/qna
     * @param pageDTO - 클라이언트에서 요청한 페이지 정보
     * @return - 페이징 처리된 QNA 게시글 리스트
     */
    @GetMapping
    public ApplicationResponse<QnaListPageResponseDTO> getList(
            PageDTO pageDTO
    ) {
        log.info("GET : /qna?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

        QnaListPageResponseDTO list = qnaService.getList(pageDTO);

        return ApplicationResponse.ok(list);
    }

    /**
     * [GET] /api/ddamddam/qna/{boardId}
     * @param boardId - 게시글의 인덱스번호
     * @return QNA 게시글 1개 상세조회
     */
    @GetMapping("/{boardId}")
    public ApplicationResponse<?> getDatail(
            @PathVariable("boardId") Long boardId
    ) {
        log.info("GET : /qna/{}", boardId);

        QnaDetailResponseDTO dto = qnaService.getDetail(boardId);

        return ApplicationResponse.ok(dto);
    }
}
