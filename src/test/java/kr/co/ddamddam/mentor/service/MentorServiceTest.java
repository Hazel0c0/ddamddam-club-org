package kr.co.ddamddam.mentor.service;

import kr.co.ddamddam.mentor.dto.page.PageDTO;
import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MentorServiceTest {

    @Autowired
    private MentorService mentorService;

    @Test
    @DisplayName("게시글 작성이 성공해야한다")
    void writeTest(){

        MentorListResponseDTO mentorListResponseDTO = mentorService.write(MentorWriteRequestDTO.builder()
                .title("제발!")
                .content("됐냐?!")
                .subject("backend")
                .current("신입")
                .build(), PageDTO.builder()
                .page(1)
                .size(9)
                .sort("mentorDate")
                .build()
        );

        System.out.println("mentorListResponseDTO = " + mentorListResponseDTO);
    }

}