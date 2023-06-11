package kr.co.ddamddam.review.service;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.repository.CompanyRepository;
import kr.co.ddamddam.review.dto.request.ReviewModifyRequestDTO;
import kr.co.ddamddam.review.dto.request.ReviewWriteRequestDTO;
import kr.co.ddamddam.review.dto.response.ReviewDetailResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListResponseDTO;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final CompanyRepository companyRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewListResponseDTO getList(PageDTO pageDTO, List<String> subjects){
        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize()
        );

        //게시글 목록 조회
        Page<Review> reviews = reviewRepository.findAll(pageable);

        List<Review> reviewList = reviews.getContent();
        List<ReviewDetailResponseDTO> reviewDetailResponseDTOList = reviewList.stream().map(review -> {
           ReviewDetailResponseDTO dto = new ReviewDetailResponseDTO();

           dto.setReviewTitle(review.getReviewTitle());
           dto.setReviewContent(review.getReviewContent());
           dto.setReviewJob(review.getReviewJob());
           dto.setReviewDate(review.getReviewDate());
           dto.setReviewRating(review.getReviewRating());
           dto.setReviewCompany(review.getCompany());

           return dto;
        }).collect(toList());

        return ReviewListResponseDTO.builder()
                .count(reviewDetailResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Review>(reviews))
                .responseDTOList(reviewDetailResponseDTOList)
                .build();
    }

    public ReviewDetailResponseDTO getDetail(Long reviewIdx){
        Optional<Review> reviewOptional = reviewRepository.findById(reviewIdx);
        Review review = reviewOptional.get();
        ReviewDetailResponseDTO dto = new ReviewDetailResponseDTO();

        dto.setReviewTitle(review.getReviewTitle());
        dto.setReviewContent(review.getReviewContent());
        dto.setReviewJob(review.getReviewJob());
        dto.setReviewDate(review.getReviewDate());
        dto.setReviewRating(review.getReviewRating());
        dto.setReviewCompany(review.getCompany());

        return dto;

    }

    //게시글 작성
    public ReviewDetailResponseDTO write(ReviewWriteRequestDTO dto, Long userIdx){
        Review review = dto.toEntity();
        Optional<User> optionalUser = userRepository.findById(userIdx);
        review.setUser(optionalUser.get());
        Review saved = reviewRepository.save(review);

        return getDetail(saved.getReviewIdx());
    }

    //수정
    public ReviewDetailResponseDTO modify(ReviewModifyRequestDTO dto) throws RuntimeException{
        Optional<Review> targetReview = reviewRepository.findById(dto.getReviewIdx());

        if (targetReview.isPresent()){
            Review review = targetReview.get();
            review.setReviewTitle(dto.getReviewTitle());
            dto.setReviewContent(review.getReviewContent());
            dto.setReviewJob(review.getReviewJob());
            dto.setReviewDate(review.getReviewDate());
            dto.setReviewRating(review.getReviewRating());
            dto.setReviewCompany(review.getCompany());

            reviewRepository.save(review);
        }else {
            throw new RuntimeException("없는 게시판입니다");
        }
        return getDetail(dto.getReviewIdx());

    }

    //게시판 삭제
    public void delete(Long reviewIdx) throws RuntimeException{
        Optional<Review> targetReview = reviewRepository.findById(reviewIdx);
        if (targetReview.isPresent()){
            reviewRepository.delete(targetReview.get());
        } else {
            throw new RuntimeException("없는 게시판입니다");
        }
    }


}
