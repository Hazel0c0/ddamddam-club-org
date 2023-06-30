package kr.co.ddamddam.qna.qnaBoard.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.qna.qnaBoard.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.request.QnaInsertRequestDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaTopListResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;
import static kr.co.ddamddam.common.response.ResponseMessage.FAIL;

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
//        log.info("GET : /qna?page={}&size={} - 게시글 전체보기", pageDTO.getPage(), pageDTO.getSize());

        QnaListPageResponseDTO qnaList = qnaService.getList(pageDTO);

        return ApplicationResponse.ok(qnaList);
    }


    /**
     * QNA 게시글 상세조회
     * [GET] /api/ddamddam/qna/{boardId}
     * @param tokenUserInfo - 로그인 중인 유저 정보
     * @param boardIdx - 게시글의 인덱스번호
     * @return 게시글의 상세정보를 담은 DTO
     */
    @GetMapping("/{boardIdx}")
    public ApplicationResponse<?> getDatail(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @PathVariable("boardIdx") Long boardIdx
    ) {
//        log.info("GET : /qna/{} - 게시글 상세조회", boardIdx);

        QnaDetailResponseDTO qnaDetail = qnaService.getDetail(tokenUserInfo, boardIdx);

        return ApplicationResponse.ok(qnaDetail);
    }

    /**
     * QNA 게시글 주간 조회순 TOP3 조회
     * [GET] /api/ddamddam/qna/top
     * ❗ 주간 조회수가 같을 경우, 댓글 개수가 많은 게시글이 우선 조회됩니다.
     * @return - QNA 전체 게시글 중 주간 조회수가 높은 상위 3개 게시글 리스트
     */
    @GetMapping("/top")
    public ApplicationResponse<?> getListTop3() {
//        log.info("GET : /qna/top - 게시글 TOP3 보기");

        List<QnaTopListResponseDTO> qnaListTop3 = qnaService.getListTop3ByView();

        return ApplicationResponse.ok(qnaListTop3);
    }

    /**
     * QNA 게시글 생성
     * [POST] /api/ddamddam/qna/write
     * ❗ dto 의 hashtagList 는 빈 배열로라도 받아야 합니다.
     * @param tokenUserInfo - 로그인 중인 유저 정보
     * @param dto - 게시글 제목, 게시글 내용
     * @return 작성된 게시글의 index (index 를 통해 작성 게시글의 상세 페이지로 이동)
     */
    @PostMapping("/write")
    public ApplicationResponse<?> writeBoard(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @Validated @RequestBody QnaInsertRequestDTO dto,
            BindingResult bindResult
    ) {
//        log.info("POST : /qna/write - 게시글 생성 {}", dto);

        if (bindResult.hasErrors()) {
            return ApplicationResponse.bad(INVALID_PARAMETER);
        }

        Long boardIdx = qnaService.writeBoard(tokenUserInfo, dto);

        return ApplicationResponse.ok(boardIdx);
    }

    /**
     * QNA 게시글 삭제
     * [DELETE] /api/ddamddam/qna/delete/{boardIdx}
     * ❗ 채택이 완료된 게시글은 삭제가 불가능합니다.
     * @param boardIdx - 삭제할 게시글의 index
     * @return - 삭제 성공시 SUCCESS
     *          - 삭제 실패시 FAIL
     */
    @DeleteMapping("/delete/{boardIdx}")
    public ApplicationResponse<?> deleteBoard(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @PathVariable Long boardIdx
    ) {
//        log.info("DELETE : /qna/delete/{} - 게시글 삭제", boardIdx);

        ResponseMessage result = qnaService.deleteBoard(tokenUserInfo, boardIdx);

        if (result == ResponseMessage.FAIL) {
            return ApplicationResponse.bad(result);
        }

        return ApplicationResponse.ok(result);
    }

    /**
     * 게시글 수정
     * [PATCH] /api/ddamddam/qna/modify/{boardIdx}
     * ❗ 채택이 완료된 게시글은 수정이 불가능합니다.
     * @param dto - 게시글 제목, 게시글 내용 / boardTitle, boardContent
     * @return 수정 성공시 SUCCESS, 수정 실패시 FAIL
     */
    @PatchMapping("/modify/{boardIdx}")
    public ApplicationResponse<?> modifyBoard(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @Validated @RequestBody QnaInsertRequestDTO dto,
            BindingResult bindResult
    ) {
//        log.info("PATCH : /qna/modify - 게시글 수정, payload - {}", dto);

        if (bindResult.hasErrors()) {
            return ApplicationResponse.bad(INVALID_PARAMETER);
        }

        ResponseMessage result = qnaService.modifyBoard(tokenUserInfo, dto);

        if (result == FAIL) {
            ApplicationResponse.bad(result);
        }

        return ApplicationResponse.ok(result);
    }

    /**
     * 게시글 검색 (게시글 제목, 본문, 해시태그로 검색됩니다.)
     * @param keyword - 검색 키워드
     *             ❗ 검색 키워드 없는 경우 '' 로 보내주세요
     * @param sort - 정렬 키워드 (전체, 미채택, 채택)
     *             ❗ 전체 : '', 미채택 : 'N', 채택 : 'Y' 로 보내주세요
     * @param pageDTO - 클라이언트에서 보낸 페이지 번호
     * @return
     */
    @GetMapping("/search")
    public ApplicationResponse<?> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("sort") String sort,
            PageDTO pageDTO
    ){
//        log.info("GET : /qna/search?page=1&size=10keyword={}&sort={}& - 게시글 제목, 본문, 해시태그로 검색", keyword, sort);

        QnaListPageResponseDTO qnaList = qnaService.getKeywordList(keyword, sort, pageDTO);

        return ApplicationResponse.ok(qnaList);
    }

//    *
//     * 게시글 조회수 상승
//     * @param boardIdx - 조회수를 상승시킬 게시글의 index
//     * @return - 상승 성공시 SUCCESS, 실패시 FAIL
//
//    @PatchMapping("/{boardIdx}/views")
//    public ApplicationResponse<?> updateViewCount(
//            @PathVariable Long boardIdx
//    ) {
//        log.info("PATCH : /qna/{}/views - 조회수 상승", boardIdx);
//
//        ResponseMessage result = qnaService.updateViewCount(boardIdx);
//
//        if (result == FAIL) {
//            ApplicationResponse.bad(result);
//        }
//
//        return ApplicationResponse.ok(result);
//    }
//    /**
//     * QNA 게시글 채택 완료 처리
//     * @param boardIdx - 채택 완료 처리를 할 게시글의 index
//     * @return - 채택 성공시 SUCCESS, 실패시 FAIL
//     */
//    @PatchMapping("/{boardIdx}/adopts")
//    public ApplicationResponse<?> adoptQnaBoard(
//            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
//            @PathVariable Long boardIdx
//    ) {
//        log.info("PATCH : /qna/{}/adopts - 게시글 채택 완료 상태로 변경", boardIdx);
//
//        ResponseMessage result = qnaService.adoptQnaBoard(tokenUserInfo, boardIdx);
//
//        if (result == FAIL) {
//            ApplicationResponse.bad(result);
//        }
//
//        return ApplicationResponse.ok(result);
//    }
//    /**
//     * QNA 채택완료 게시글만 정렬 조회
//     * [GET] ex) /api/ddamddam/qna/adopts?page=2
//     * @param pageDTO - 클라이언트에서 요청한 페이지 정보
//     * @return 페이징 처리된 QNA 채택완료 게시글 리스트
//     */
//    @GetMapping("/adopts")
//    public ApplicationResponse<?> getListAdoption(
//            PageDTO pageDTO
//    ) {
//        log.info("GET : /qna/adopts - 채택된 게시글만 보여주기");
//
//        QnaListPageResponseDTO qnaListAdopt = qnaService.getListAdoption(pageDTO);
//
//        return ApplicationResponse.ok(qnaListAdopt);
//    }
//
//    /**
//     * QNA 미채택 게시글만 정렬 조회
//     * [GET] ex) /api/ddamddam/qna/non-adopts?page=3
//     * @param pageDTO - 클라이언트에서 요청한 페이지 정보
//     * @return 페이징 처리된 QNA 채택완료 게시글 리스트
//     */
//    @GetMapping("/non-adopts")
//    public ApplicationResponse<?> getListNonAdoption(
//            PageDTO pageDTO
//    ) {
//        log.info("GET : /qna/adopts - 미채택 상태인 게시글만 보여주기");
//
//        QnaListPageResponseDTO qnaListNonAdopt = qnaService.getListNonAdoption(pageDTO);
//
//        return ApplicationResponse.ok(qnaListNonAdopt);
//    }

}
