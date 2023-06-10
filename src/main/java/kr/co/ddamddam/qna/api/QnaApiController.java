package kr.co.ddamddam.qna.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.request.QnaInsertRequestDTO;
import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaTopListResponseDTO;
import kr.co.ddamddam.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * QNA 게시판 Controller
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/qna")
public class QnaApiController {

    private final QnaService qnaService;

    /**
     * QNA 게시글 전체보기
     * [GET] /api/ddamddam/qna
     * @param pageDTO - 클라이언트에서 요청한 페이지 정보
     * @return - 페이징 처리된 QNA 게시글 리스트
     */
    @GetMapping
    public ApplicationResponse<?> getList(
            PageDTO pageDTO
    ) {
        log.info("GET : /qna?page={}&size={} - 게시글 전체보기", pageDTO.getPage(), pageDTO.getSize());

        QnaListPageResponseDTO list = qnaService.getList(pageDTO);

        return ApplicationResponse.ok(list);
    }

    /**
     * QNA 게시글 조회순 TOP3 조회
     * [GET] /api/ddamddam/qna/top
     * @return - QNA 전체 게시글 중 조회수가 높은 상위 3개 게시글 리스트
     */
    @GetMapping("/top")
    public ApplicationResponse<?> getListTop3() {
        log.info("GET : /qna/top - 게시글 TOP3 보기");

        List<QnaTopListResponseDTO> listTop3 = qnaService.getListTop3();

        return ApplicationResponse.ok(listTop3);
    }

    /**
     * QNA 게시글 상세조회
     * [GET] /api/ddamddam/qna/{boardId}
     * @param boardId - 게시글의 인덱스번호
     * @return 게시글의 상세정보를 담은 DTO
     */
    @GetMapping("/{boardId}")
    public ApplicationResponse<?> getDatail(
            @PathVariable("boardId") Long boardId
    ) {
        log.info("GET : /qna/{} - 게시글 상세조회", boardId);

        QnaDetailResponseDTO dto = qnaService.getDetail(boardId);

        return ApplicationResponse.ok(dto);
    }

    /**
     * QNA 게시글 생성
     * [POST] /api/ddamddam/qna/write
     * @param dto - 게시글 제목, 게시글 내용
     * @return 작성한 게시글의 상세정보를 담은 DTO
     */
    @PostMapping("/write")
    public ApplicationResponse<?> writeBoard(
//            Long userIdx,
            @RequestBody QnaInsertRequestDTO dto
    ) {
        log.info("POST : /qna/write - 게시글 생성 {}", dto);

        // TODO : 토큰 방식으로 로그인한 회원의 idx 를 가져와서 Service 파라미터로 넣는 처리 필요
        Long userIdx = 1L;

        QnaDetailResponseDTO responseDTO = qnaService.writeBoard(userIdx, dto);

        return ApplicationResponse.ok(responseDTO);
    }

    /**
     * QNA 게시글 삭제
     * [DELETE] /api/ddamddam/qna/delete/{boardIdx}
     * @param boardIdx - 삭제할 게시글의 index
     * @return - 삭제 성공시 SUCCESS, 삭제 실패시 FAIL
     */
    @DeleteMapping("/delete/{boardIdx}")
    public ApplicationResponse<?> deleteBoard(
            @PathVariable Long boardIdx
    ) {
        log.info("DELETE : /qna/delete/{} - 게시글 삭제", boardIdx);

        ResponseMessage result = qnaService.deleteBoard(boardIdx);

        return ApplicationResponse.ok(result);
    }

    /**
     * 게시글 조회수 상승
     * @param boardIdx - 조회수를 상승시킬 게시글의 index
     * @return - 상승 성공시 SUCCESS, 상승 실패시 FAIL
     */
    @PatchMapping("/{boardIdx}/views")
    public ApplicationResponse<?> updateViewCount(
            @PathVariable Long boardIdx
    ) {
        log.info("PATCH : /qna/{}/views - 조회수 상승", boardIdx);

        ResponseMessage result = qnaService.updateViewCount(boardIdx);

        return ApplicationResponse.ok(result);
    }

}
