package kr.co.ddamddam.review.api;

import kr.co.ddamddam.review.dto.page.PageDTO;
import kr.co.ddamddam.review.dto.request.ReviewModifyRequestDTO;
import kr.co.ddamddam.review.dto.request.ReviewWriteRequestDTO;
import kr.co.ddamddam.review.dto.response.ReviewDetailResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListPageResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewTopListResponseDTO;
import kr.co.ddamddam.review.service.ReviewNotFoundException;
import kr.co.ddamddam.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewApiController {

    private final ReviewService reviewService;

    //전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO){
        log.info("api/ddamddam/reviews/list?page{}&size={}&sort={}",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        ReviewListPageResponseDTO dto = reviewService.getList(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    //상세 페이지 조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam Long reviewIdx)  {
        log.info("api/ddamddam/reviews/detail?reviewIdx={}",reviewIdx);
        ReviewDetailResponseDTO dto = null;
        try {
            dto = reviewService.getDetail(reviewIdx);
        } catch (ReviewNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(dto);
    }

//    ///더미 데이터 넣어 놓는용...
//    @PostMapping("/insert")
//    public  ResponseEntity<?> write(@Validated @RequestBody ReviewWriteRequestDTO dto) throws ReviewNotFoundException {
//        log.info("POST : /reviews/write - 게시글 생성 {}", dto);
//        Long userIdx = 1L;
//        ReviewDetailResponseDTO responseDTO = reviewService.write(dto,userIdx);
//        return ResponseEntity.ok().body(responseDTO);
//    }


    //키워드 검색
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword,
                                    PageDTO pageDTO){
        log.info("api/ddamddam/reviews/search?keyword={}&page{}&size={}&sort={}", keyword,pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        ReviewListPageResponseDTO reviewList = reviewService.getKeywordList(keyword,pageDTO);
        return ResponseEntity.ok().body(reviewList);
    }

    //조회순으로 조회하기
    @GetMapping("/view")
    public ResponseEntity<?> OrderByView(PageDTO pageDTO){
        log.info("api/ddamddam/reviews/view?&page={}&size={}&sort={},",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        ReviewListPageResponseDTO reviewList = reviewService.getListOrderByView(pageDTO);
        return ResponseEntity.ok().body(reviewList);
    }

    //평점순으로 조회하기
    @GetMapping("/rating")
    public ResponseEntity<?> OrderByrating(PageDTO pageDTO){
        log.info("api/ddamddam/reviews/rating?&page={}&size={} ");
        ReviewListPageResponseDTO reviewList = reviewService.getListOrderByRateDesc(pageDTO);
        return ResponseEntity.ok().body(reviewList);
    }

    //최신순으로 조회하기
//    @GetMapping("/latest")
//    public ResponseEntity<?> latest(){
//        log.info("api/ddamddam/reviews/latest ");
//        List<ReviewListResponseDTO> dto =  reviewService.getListDateDesc();
//        return ResponseEntity.ok().body(dto);
//    }


    //TOP3 뿌려주기
    @GetMapping("/viewTop3")
    public ResponseEntity<?> OrderByView3(){
        log.info("api/ddamddam/reviews/viewTop3");
        List<ReviewTopListResponseDTO> dto =  reviewService.getListTop3();

        return ResponseEntity.ok().body(dto);
    }



    //게시물 생성하기
    @PostMapping("/write")
    public  ResponseEntity<?> write(@Validated @RequestBody ReviewWriteRequestDTO dto) throws ReviewNotFoundException {
        log.info("POST : /reviews/write - 게시글 생성 {}", dto);
        Long userIdx = 1L;
        ReviewDetailResponseDTO responseDTO = reviewService.write(dto,userIdx);
        return ResponseEntity.ok().body(responseDTO);
    }

    //게시물 수정
    @RequestMapping(value = "/modify",method = {RequestMethod.PUT,RequestMethod.PATCH})
    public ResponseEntity<?> modify(
            @Validated @RequestBody ReviewModifyRequestDTO dto
    ){
        log.info("/api/ddamddam/reviews PUT!! - payload {}",dto);

        try {
            ReviewDetailResponseDTO responseDTO = reviewService.modify(dto);
            return ResponseEntity.ok().body(responseDTO);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다");
        } catch (ReviewNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //게시물 삭제
    @DeleteMapping("/delete/{reviewIdx}")
    public ResponseEntity<?> delete(
            @PathVariable Long reviewIdx
    ){
        log.info("/api/ddamddam/delete/reviews/{} DELETE!!",reviewIdx);
        try {
            reviewService.delete(reviewIdx);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다");
        }

        return ResponseEntity.ok().body("삭제 성공!");
    }






}
