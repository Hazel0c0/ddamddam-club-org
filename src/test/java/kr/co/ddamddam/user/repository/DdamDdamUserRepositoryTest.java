package kr.co.ddamddam.user.repository;

import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
class DdamDdamUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("유저 더미데이터 20명 생성")
    void insertBulk() {
        //given

        for (int i = 1; i <= 20; i++) {

            int index1 = (int) (Math.random() + 1); // 0 또는 1
            int index2 = (int) (Math.random() * 4); // 0 ~ 4
            int year = (int) (Math.random() * 31 + 1970); // 1970 ~ 2000 사이의 랜덤 정수
            int month = (int) (Math.random() * 12 + 1); // 1 ~ 12 사이의 랜덤 정수
            int day = (int) (Math.random() * 30 + 1); // 1 ~ 30 사이의 랜덤 정수
            int career = (int) (Math.random() * 10 + 1); // 1 ~ 10 사이의 랜덤 정수
            UserPosition[] userPosition = {UserPosition.BACKEND, UserPosition.FRONTEND};
            String[] name = {"조예원", "김태근", "최예진", "채지원", "조경훈"};
            String encoded = encoder.encode("qwer1234!");

            User user = User.builder()
                    .userPassword(encoded)
                    .userEmail("test" + i + "@t.t")
                    .userName(name[index2])
                    .userNickname("닉네임" + i)
                    .userBirth(LocalDate.of(year, month, day))
                    .userPosition(userPosition[index1])
                    .userCareer(career)
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
    
    @Test
    @DisplayName("토큰 서명 해시값 생성하기")
    void makeSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[64]; // 64 bytes = 512 bits
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("\n\n\n");
        System.out.println(encodedKey);
        System.out.println("\n\n\n");
    }
}
