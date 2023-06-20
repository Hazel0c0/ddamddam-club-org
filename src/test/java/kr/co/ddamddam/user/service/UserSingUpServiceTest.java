package kr.co.ddamddam.user.service;

import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;



@SpringBootTest
//@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
public class UserSingUpServiceTest {

    @Autowired
   UserSignUpService userSingUpService;

    @Test
    @DisplayName("중복된 이메일로 회원가입을 시도하면 RuntimeException 발생")
    void validateEmailTest() {
        // given
        String email = "test50@test.com";

        UserRequestSignUpDTO dto = UserRequestSignUpDTO.builder()
                .userEmail(email)
                .userPw("aaa111!!!")
                .userName("최예진")
                .userNickName("냥냥냥냥")
                .userPosition("BACKEND")
                .userCareer(3)
                .userBirth(LocalDate.parse("1999-03-09"))
                .build();

        // when, then
        UserSignUpResponseDTO userSignUpResponseDTO = userSingUpService.create(dto);

    }

    @Test
    @DisplayName("유저 더미데이터 30명 생성")
    void insertBulk() {
        //given

        for (int i = 1; i <= 30; i++) {

            int index1 = (int) (Math.random() * 2); // 0 또는 1
            int index2 = (int) (Math.random() * 5); // 0 ~ 4
            int index3 = (int) (Math.random() * 5); // 0 ~ 4
            String year = String.valueOf((int) (Math.random() * 31 + 1970)); // 1970 ~ 2000 사이의 랜덤 정수
            String month = String.valueOf((int) (Math.random() * 12 + 1)); // 1 ~ 12 사이의 랜덤 정수
            String day = String.valueOf((int) (Math.random() * 30 + 1)); // 1 ~ 30 사이의 랜덤 정수
            int career = (int) (Math.random() * 10 + 1); // 1 ~ 10 사이의 랜덤 정수
            UserPosition[] userPosition = {UserPosition.BACKEND, UserPosition.FRONTEND};
            String[] name = {"조예원", "김태근", "최예진", "채지원", "조경훈"};

            UserRequestSignUpDTO dto = UserRequestSignUpDTO.builder()
                    .userEmail("test" + i + "@t.t")
                    .userPw("qwer1234!")
                    .userName(name[index2])
                    .userNickName(name[index3])
                    .userPosition(String.valueOf(userPosition[index1]))
                    .userCareer(career)
                    .userBirth(LocalDate.parse("1990-01-01"))
                    .build();

            // when, then
            UserSignUpResponseDTO userSignUpResponseDTO = userSingUpService.create(dto);
        }
    }
}