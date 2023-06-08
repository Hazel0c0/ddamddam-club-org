package kr.co.ddamddam.mentor.api;


import kr.co.ddamddam.mentor.dto.page.PageDTO;
import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import kr.co.ddamddam.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorApiController {

    // 리소스 : 게시물 (Mentors)
/*
     게시물 목록 조회:  /mentors       - GET
 */
    private final MentorService mentorService;


    // 멘토페이지 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO){

        log.info("/api/mentors/list?page{}&size={}&sort={}",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());

        MentorListResponseDTO dto = mentorService.getList(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    // 게시물 상세 페이지 조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(Long mentorIdx){
        log.info("/api/mentors/detail?mentorIdx={}",mentorIdx);
        MentorDetailResponseDTO dto = mentorService.getDetail(mentorIdx);

        return ResponseEntity.ok().body(dto);
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<?> write(@Validated @RequestBody MentorWriteRequestDTO dto, PageDTO pageDTO) {
        // 요청 URL(POST) /api/mentors?page{}&size={}&sort={}
        // payload{
        //  "title": "게시글 제목",
        //  "content": "게시글 내용",
        //  "subject": "게시글 주제",
        //  "current": "현재 상태"
        //}
        log.info("/api/mentors?page{}&size={}&sort={} POST!! - payload {}"
                ,pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort(), dto);
        // 로그인한 토큰방식으로 user_idx값 받아와 서비스 파라미터에 넣기
        MentorListResponseDTO responseDTO = mentorService.write(dto, pageDTO);

        return ResponseEntity.ok().body(responseDTO);
    }


    // 좋아요 기능 만들기(멘토에 대한 좋아요인지 게시판에 대한 좋아요인지)


}
