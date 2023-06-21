package kr.co.ddamddam.review.repository;

import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

// TODO: 취업후기 게시판 테스트
@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("취업후기 게시판 9개 생성")
    void bulkInsert() {
        Optional<User> userOptional = userRepository.findById(2L);
        for (int i = 1; i < 10 ; i++) {
            reviewRepository.save(
                    Review.builder()
                            .reviewTitle(i+"여기 완전 좋지렁")
                            .reviewContent(i+"여기서 일하면 과자 많이 먹을 수 있어요, 커피도 맛있음")
                            .reviewTenure(i)
                            .reviewRating((float) (0+0.5*i))
                            .reviewJob("백엔드"+i)
                            .reviewCompany("감귤회사"+i)
                            .reviewLocation("서울특별시 용산구")
                            .user(userOptional.get())
                            .reviewView(i)
                            .build()
            );

        }
    }

}