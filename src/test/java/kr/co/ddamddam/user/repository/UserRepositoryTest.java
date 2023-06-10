package kr.co.ddamddam.user.repository;

import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void insertUser() {
        User user = User.builder()
                .userid("test")
                .userPw("1234")
                .userEmail("test@test.com")
                .userName("테스트")
                .userNickname("테스트")
                .userBirth(LocalDate.of(1990, 5, 15))
                .userPosition(UserPosition.BACKEND)
                .userCareer(3)
                .userRole(UserRole.COMMON)
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("유저 더미데이터 50명 생성")
    void bulkInsert() {
        //given

        for (int i = 1; i <= 50; i++) {

            int index = (int) (Math.random() * 1); // 0 또는 1
            int year = (int) (Math.random() * 31 + 1970); // 1970 ~ 2000 사이의 랜덤 정수
            int month = (int) (Math.random() * 12 + 1); // 1 ~ 12 사이의 랜덤 정수
            int day = (int) (Math.random() * 30 + 1); // 1 ~ 30 사이의 랜덤 정수
            UserPosition[] userPosition = {UserPosition.BACKEND, UserPosition.FRONTEND};

            User user = User.builder()
                    .userid("test" + i)
                    .userPw("1234")
                    .userEmail("test" + i + "@test.com")
                    .userName("테스트" + i)
                    .userNickname("테스트" + i)
                    .userBirth(LocalDate.of(year, month, day))
                    .userPosition(userPosition[index])
                    .userCareer(3)
                    .userRole(UserRole.COMMON)
                    .build();
            userRepository.save(user);
        }
        //when
        //then
    }

    @Test
    @DisplayName("1번 회원의 이메일은 'test@test.com'여야 한다.")
    void viewTest() {
        //given
        //when
        User user = userRepository.findById(1L).orElseThrow();
        //then
        assertEquals("test@test.com", user.getUserEmail());
    }
}