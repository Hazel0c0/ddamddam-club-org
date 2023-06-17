package kr.co.ddamddam.mypage.service;

import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public void getList(Long enterUser) {

        User user = userRepository.findById(enterUser).orElseThrow();
//        List<Mentor> mentor = user.getMentor();
        List<Review> review = user.getReview();
        List<Qna> qna = user.getQna();
    }
}
