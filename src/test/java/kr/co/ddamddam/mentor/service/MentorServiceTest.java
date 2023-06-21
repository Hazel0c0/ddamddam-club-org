package kr.co.ddamddam.mentor.service;

import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MentorServiceTest {

    @Autowired
    private MentorService mentorService;

//    @Test
//    @DisplayName("게시글 작성이 성공해야한다")
//    void writeTest(){
//        Long userIdx = 2L;
//        MentorDetailResponseDTO mentorDetailResponseDTO = mentorService.write(MentorWriteRequestDTO.builder()
//                .mentorTitle("제발!@#")
//                .mentorContent("됐냐?!!!")
//                .mentorSubject("back")
//                .mentorCurrent("신입")
//                .build(),
//                userIdx);
//
//        System.out.println("mentorDetailResponseDTO = " + mentorDetailResponseDTO);
//    }

//    @Test
//    @DisplayName("게시글 수정이 성공해야한다")
//    void modifyTest(){
//
////        mentorService.modify(MentorModifyRequestDTO.builder()
////                        .mentorIdx(5L)
////                        .mentorTitle("고고")
////                        .mentorContent("야호")
////                        .mentorSubject("백엔드")
////                        .mentorCurrent("9년")
////                .build());
//
//        MentorDetailResponseDTO detail = mentorService.getDetail(2L);
//        System.out.println("detail = " + detail);
//
//    }

    @Test
    @DisplayName("게시글 11번이 삭제 성공해야한다")
    void deleteTest(){

//        mentorService.delete(5L);
    }

    @Test
    @DisplayName("게시글 11번의 멘티 생성 성공해야한다")
    void saveMenteeTest(){
        Long userIdx = 2L;
        Long mentorIdx = 1L;

//        mentorService.menteeSave(mentorIdx,userIdx, enterUserIdx);

    }

}