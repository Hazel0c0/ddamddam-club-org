package kr.co.ddamddam.user.service;

import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
import kr.co.ddamddam.user.entity.UserPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static kr.co.ddamddam.user.entity.UserPosition.BACKEND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("중복된 이메일로 회원가입을 시도하면 RuntimeException발생")
    void validateEmailTest() {
        //given
        String email = "abc@abc.com";

        UserRequestSignUpDTO dto
                = UserRequestSignUpDTO.builder()
                .userEmail(email)
                .userPw("aaa111!!!")
                .userName("최예진")
                .userNickName("냥냥냥냥")
                .userPosition(BACKEND)
                .userCareer(3)
                .userBirth(LocalDate.parse("1999-03-09"))
                .build();

        //when
        //then
        assertThrows(RuntimeException.class,
                () -> {userService.create(dto);}
        );
    }


}