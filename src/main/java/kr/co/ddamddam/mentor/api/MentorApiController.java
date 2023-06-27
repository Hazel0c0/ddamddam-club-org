package kr.co.ddamddam.mentor.api;


import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mentor.dto.page.MentorPageDTO;
import kr.co.ddamddam.mentor.dto.request.MentorModifyRequestDTO;
import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MenteeListResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import kr.co.ddamddam.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/mentors")
@CrossOrigin
public class MentorApiController {

    // 리소스 : 게시물 (Mentors)
/*
     게시물 목록 조회:  /mentors       - GET
 */
    private final MentorService mentorService;


    // 멘토페이지 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(
            MentorPageDTO mentorPageDTO
    ){
//        log.info("/api/ddamddam/mentors/list?page{}&size={}", pageDTO.getPage(), pageDTO.getSize());

        MentorListResponseDTO dto = mentorService.getList(mentorPageDTO);

        return ResponseEntity.ok().body(dto);
    }

    // 멘토페이지 주제검색 목록 조회
    @GetMapping("/sublist")
    public ResponseEntity<?> list(
            MentorPageDTO mentorPageDTO, @RequestParam(required = false) String[] subjects
    ){
//        log.info("/api/ddamddam/mentors/list?page{}&size={}&subjects={a,b}", pageDTO.getPage(), pageDTO.getSize(),subjects);

        List<String> subjectList = subjects != null ? Arrays.asList(subjects) : Collections.emptyList();

        MentorListResponseDTO dto = mentorService.getSubList(mentorPageDTO, subjectList);

        return ResponseEntity.ok().body(dto);
    }


    // 게시물 상세 페이지 조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(
            Long mentorIdx
            ,@AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ){
//        log.info("/api/ddamddam/mentors/detail?mentorIdx={}",mentorIdx);
        MentorDetailResponseDTO dto = mentorService.getDetail(mentorIdx, tokenUserInfo);
//        log.info("detail!!! : {}",dto);
        return ResponseEntity.ok().body(dto);
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<?> write(
            @Validated @RequestBody MentorWriteRequestDTO dto
            ,@AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        // 요청 URL(POST) /api/mentors
        // payload{
        //  "mentorTitle": "게시글 제목",
        //  "mentorContent": "게시글 내용",
        //  "mentorSubject": "게시글 주제",
        //  "mentorCurrent": "현재 상태"
        // 게시글 작성 후 상세 모달 보기로 일단 처리
        //}
        log.info("/api/ddamddam/mentors POST!! - payload {}",dto);
        // 로그인한 토큰방식으로 user_idx값 받아와 서비스 파라미터에 넣기
        MentorDetailResponseDTO responseDTO = mentorService.write(dto,userInfo);
        log.info("write RES : {}", responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    // 게시글 수정
    @RequestMapping(value = "/modify",method = {RequestMethod.PUT,RequestMethod.PATCH})
    public ResponseEntity<?> modify(
            @Validated @RequestBody MentorModifyRequestDTO dto
            ,@AuthenticationPrincipal TokenUserInfo userInfo
            ){
        // 요청 URL(PUT, PATCH) /api/mentors
        // payload{
        //  "mentorIdx" : 게시글 번호,
        //  "mentorTitle": "게시글 제목",
        //  "mentorContent": "게시글 내용",
        //  "mentorSubject": "게시글 주제",
        //  "mentorCurrent": "현재 상태"
        // 게시글 수정 후 상세 모달 보기로 일단 처리
        //}
//        log.info("/api/ddamddam/mentors PUT!! - payload {}",dto);


        try {
            MentorDetailResponseDTO responseDTO = mentorService.modify(dto,userInfo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다");
        }

    }

    // 게시글 삭제
    @DeleteMapping("/{mentorIdx}")
    public ResponseEntity<?> delete(
            @PathVariable Long mentorIdx
            ,@AuthenticationPrincipal TokenUserInfo userInfo
    ){
//        log.info("/api/ddamddam/mentors/{} DELETE!!",mentorIdx);
        Long userIdx = Long.valueOf(userInfo.getUserIdx());
        try {
            mentorService.delete(mentorIdx,userIdx);
            List<String> subjects = new ArrayList<>();
                MentorListResponseDTO list = mentorService.getSubList(new MentorPageDTO(),subjects);
            return ResponseEntity.ok().body(list);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다");
        }
    }

    // 멘티 확정 시 생성
    @RequestMapping(value = "/mentee/{mentorIdx}/{roomId}",method = {RequestMethod.PUT,RequestMethod.PATCH})
    public ResponseEntity<?> menteeSave(
            @PathVariable Long mentorIdx
            ,@PathVariable Long roomId
            ,@AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ){
        try {
            int menteeSave = mentorService.menteeSave(mentorIdx, roomId, tokenUserInfo);

            return ResponseEntity.ok().body(menteeSave);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    // 멘티 리스트
    @GetMapping("/mentee/list/{mentorIdx}")
    public ResponseEntity<?> menteeList(
            @PathVariable Long mentorIdx
            ,@AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ){
        MenteeListResponseDTO menteeList = mentorService.getMenteeList(mentorIdx, tokenUserInfo);

        log.info("menteeList : {}", menteeList);

        return ResponseEntity.ok().body(menteeList);
    }

}
