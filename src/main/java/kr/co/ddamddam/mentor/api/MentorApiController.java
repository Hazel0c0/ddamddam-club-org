package kr.co.ddamddam.mentor.api;


import kr.co.ddamddam.mentor.dto.page.PageDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import kr.co.ddamddam.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

 // 좋아요 기능 만들기


}
