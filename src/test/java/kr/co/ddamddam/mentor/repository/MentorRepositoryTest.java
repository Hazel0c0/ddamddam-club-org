package kr.co.ddamddam.mentor.repository;

import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MentorRepositoryTest {

    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("멘토 게시판 9개 생성")
    void bulkInsert(){
        Optional<User> userOptional = userRepository.findById(3L);
        for (int i = 1; i < 10; i++) {
            mentorRepository.save(
                    Mentor.builder()
                            .mentorTitle("백엔드 너무 어려워요" + i)
                            .mentorContent("안녕하세요 반갑습니다 ㅣㅅㅂㅅㅂㅅㅂㅆㅂ"+i)
                            .mentorSubject("백엔드")
                            .mentorCurrent(i+"년")
                            .mentorCareer("배구")
                            .user(userOptional.get())
                            .build()
            );
        }

    }
}