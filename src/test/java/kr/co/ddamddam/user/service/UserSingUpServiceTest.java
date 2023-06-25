//package kr.co.ddamddam.user.service;
//
//import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
//import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//@Transactional
//class UserSingUpServiceTest {
//
//
//    @Autowired
//    UserSignUpService userSingUpService;
//
//    @Test
//    @DisplayName("중복된 이메일로 회원가입을 시도하면 RuntimeException 발생")
//    void validateEmailTest() {
//        // given
//        String email = "test50@test.com";
//
//        UserRequestSignUpDTO dto = UserRequestSignUpDTO.builder()
//                .userEmail(email)
//                .userPw("aaa111!!!")
//                .userName("최예진")
//                .userNickName("냥냥냥냥")
//                .userPosition("BACKEND")
//                .userCareer(3)
//                .userBirth(LocalDate.parse("1999-03-09"))
//                .build();
//
//        // when, then
//        UserSignUpResponseDTO userSignUpResponseDTO = userSingUpService.create(dto, uploadedFilePath);
//
//    }
//
//}