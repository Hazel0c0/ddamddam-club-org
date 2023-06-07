package kr.co.ddamddam.mentor.controller;

import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mentors")
@RequiredArgsConstructor
@Slf4j
public class MentorController {

    private final MentorService mentorService;

    // 전체 게시물 페이지 이동
    @GetMapping("/list")
    public String list(){
        return "mentor/list";
    }

    // 게시물 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(int mentorIdx, Model model){
        System.out.println("/mentors/detail : GET");

        MentorDetailResponseDTO dto = mentorService.getDetail(mentorIdx);
        model.addAttribute("mentor",dto);

        return "mentor/detail";
    }

    // 작성 페이지 이동
    @GetMapping("/write")
    public String write(){
        return "mentor/write";
    }

    // 수정 페이지 이동
    @GetMapping("/modify")
    public String modify(){
        return "mentor/modify";
    }


}
