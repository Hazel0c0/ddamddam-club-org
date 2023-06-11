package kr.co.ddamddam.review.api;


import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.review.dto.request.ReviewModifyRequestDTO;
import kr.co.ddamddam.review.dto.request.ReviewWriteRequestDTO;
import kr.co.ddamddam.review.dto.response.ReviewDetailResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListResponseDTO;
import kr.co.ddamddam.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/reviews")
public class ReviewApiController {

    private final ReviewService reviewService;

    //전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO, @RequestParam(required = false)List<String> keyword){
        log.info("api/ddamddam/reviews/list>page{}&size={}&sort={}",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        ReviewListResponseDTO dto = reviewService.getList(pageDTO,keyword);
        return ResponseEntity.ok().body(dto);
    }

    //상세 페이지 조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(Long reviewIdx){
        log.info("api/ddamddam/reviews/detail?reviewIdx={}",reviewIdx);
        ReviewDetailResponseDTO dto = reviewService.getDetail(reviewIdx);
        return ResponseEntity.ok().body(dto);
    }

    //게시물 생성하기
    @PostMapping
    public  ResponseEntity<?> write(@Validated @RequestBody ReviewWriteRequestDTO dto){
        Long userIdx = 2L;
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
        }

    }



    //게시물 삭제
    @DeleteMapping("/{reviewIdx}")
    public ResponseEntity<?> delete(
            @PathVariable Long reviewIdx
    ){
        log.info("/api/ddamddam/reviews/{} DELETE!!",reviewIdx);
        try {
            reviewService.delete(reviewIdx);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다");
        }

        return ResponseEntity.ok().body("삭제 성공!");
    }






}
