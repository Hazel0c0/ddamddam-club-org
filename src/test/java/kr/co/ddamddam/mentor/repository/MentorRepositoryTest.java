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
        Optional<User> userOptional = userRepository.findById(1L);
        for (int i = 1; i < 10; i++) {
            mentorRepository.save(
                    Mentor.builder()
                            .mentorTitle("백엔드 너무 어려워요" + i)
                            .mentorContent("한근두근태근 팔자막창이란 가게를 한참 굴리다 2014년 말 즈음에 폐업하고, 아래에 언급된 사정에 의해 지인들과 동업하는 형태로 고깃집 401 레스토랑을 열었다. 이름은 하하가 결혼 전에 같이 살던 친구들과 동거하던 건물의 호수[28]에서 따왔다고. 서울 마포구와 부산 해운대에 지점이 있으며, 그럭저럭 맛집으로 유명세를 타고있다. 2017년 중반부터 김종국과 동업을 하기 시작하며 하하&김종국의 401정육식당으로 개칭하였다."+i)
                            .mentorSubject("백엔드")
                            .mentorCurrent(i+"년")
                            .mentorCareer("배구")
                            .user(userOptional.get())
                            .build()
            );
        }

    }
}