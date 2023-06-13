package kr.co.ddamddam.review.service;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.review.dto.request.ReviewModifyRequestDTO;
import kr.co.ddamddam.review.dto.request.ReviewWriteRequestDTO;
import kr.co.ddamddam.review.dto.response.ReviewDetailResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListPageResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewTopListResponseDTO;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode.NOT_FOUND_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final int VIEW_COUNT = 1;
    private final CompanyRepository companyRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewListPageResponseDTO getList(PageDTO pageDTO){
        PageRequest pageable = getPageable(pageDTO);

        // 데이터베이스에서 QNA 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Review> reviews = reviewRepository.findAll(pageable);
        List<ReviewListResponseDTO> reviewListResponseDTOList = getReviewDTOList(reviews);

        //JSON 형태로 변형
        return ReviewListPageResponseDTO.builder()
                .count(reviewListResponseDTOList.size())
                .count(reviewListResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Review>(reviews))
                .responseList(reviewListResponseDTOList)
                .build();

    }

    private PageRequest getPageable(PageDTO pageDTO) {
        return PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by("reviewDate").descending()
        );
    }


    private List<ReviewListResponseDTO> getReviewDTOList(Page<Review> reviews) {

        return reviews.getContent().stream()
                .map(ReviewListResponseDTO::new)
                .collect(toList());
    }

    //게시글 상세조회
    public ReviewDetailResponseDTO getDetail(Long reviewIdx) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewIdx);

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            // 조회수를 증가시킵니다.
            increaseReviewView(reviewIdx);
            return new ReviewDetailResponseDTO(review);
        } else {
            throw new ReviewNotFoundException("Review not found with ID: " + reviewIdx);
        }

    }

    //TOP3 게시글 가져오기
    public List<ReviewTopListResponseDTO> getListTop3(){
        List<Review> reviewTopList = reviewRepository.findTop3Desc();

        return reviewTopList.stream().map(review -> ReviewTopListResponseDTO.builder()
                .boardIdx(review.getReviewIdx())
                .boardTitle(review.getReviewTitle())
                .boardView(review.getReviewView())
                .companyName(review.getCompany().getCompanyName())
                .boardRating(review.getReviewRating())
                .boardJob(review.getReviewJob())
                .boardTenure(review.getReviewTenure())
                .boardLocation(review.getCompany().getCompanyArea())
                .build()
        ).collect(toList());
    }



    //키워드 검색기능 구현하기
    public List<ReviewListResponseDTO> getKeywordList(String keyword){
        List<Review> reviewList  = reviewRepository.findByKeyword(keyword);
        return reviewList.stream()
                .map(ReviewListResponseDTO::new)
                .collect(toList());
    }

    // 최신순으로 조회하기
    public List<ReviewListResponseDTO> getListDateDesc(){
        List<Review> reviewList  = reviewRepository.findByDate();
        return reviewList.stream()
                .map(ReviewListResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 조회순으로 조회하기
    public List<ReviewTopListResponseDTO> getListOrderByView(){
        List<Review> reviewTopList = reviewRepository.findByDesc();
//        List<Review> reviewTop3List = reviewRepository.findTop3ByOrderByReviewRatingDesc();

        return reviewTopList.stream().map(review -> ReviewTopListResponseDTO.builder()
                .boardIdx(review.getReviewIdx())
                .boardTitle(review.getReviewTitle())
                .boardJob(review.getReviewJob())
                .boardRating(review.getReviewRating())
                .boardTenure(review.getReviewTenure())
                .boardView(review.getReviewView())
                .build()
        ).collect(Collectors.toList());
    }


    // 평점순으로 조회하기
    public List<ReviewListResponseDTO> getListOrderByRateDesc(){
        List<Review> reviewList  = reviewRepository.findByRate();
        return reviewList.stream()
                .map(ReviewListResponseDTO::new)
                .collect(Collectors.toList());
    }


    //게시글 작성
    public ReviewDetailResponseDTO write(ReviewWriteRequestDTO dto, Long userIdx) throws ReviewNotFoundException {
//        Optional<User> optionalUser = userRepository.findById(userIdx);
        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, userIdx);
        });

        Review saved = reviewRepository.save(dto.toEntity(user));
        ReviewDetailResponseDTO detail = getDetail(saved.getReviewIdx());
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with id: " + userIdx));

        return detail;
    }

    //수정
    public ReviewDetailResponseDTO modify(ReviewModifyRequestDTO dto) throws RuntimeException, ReviewNotFoundException {
        Optional<Review> targetReview = reviewRepository.findById(dto.getReviewIdx());

        if (targetReview.isPresent()){
            Review review = targetReview.get();
            Company company = companyRepository.findById(dto.getReviewCompany()).get();
//            Review review1 = new ReviewModifyRequestDTO().toEntity();
            review.setReviewTitle(dto.getReviewTitle());
            review.setReviewContent(dto.getReviewContent());
            review.setReviewJob(dto.getReviewJob());
            review.setReviewDate(dto.getReviewDate());
            review.setReviewRating(dto.getReviewRating());
            review.setCompany(company);

            reviewRepository.save(review);
        }else {
            throw new RuntimeException("없는 게시판입니다"+ dto.getReviewIdx());
        }
        return getDetail(dto.getReviewIdx());

    }

    //게시판 삭제
    public void delete(Long reviewIdx) throws RuntimeException{
        Optional<Review> targetReview = reviewRepository.findById(reviewIdx);
        if (targetReview.isPresent()){
            reviewRepository.delete(targetReview.get());
        } else {
            throw new RuntimeException("없는 게시판입니다" + reviewIdx);
        }
    }

    //조회수 상승
//    @Transactional
    public void increaseReviewView(Long reviewIdx) throws ReviewNotFoundException {
        Optional<Review> targetReviewOptional = reviewRepository.findById(reviewIdx);
        if (targetReviewOptional.isEmpty()) {
            throw new ReviewNotFoundException("Review not found with id: " + reviewIdx);
        }

        Review targetReview = targetReviewOptional.get();
        // 조회수를 1 증가시킵니다.
        targetReview.setReviewView(targetReview.getReviewView() + 1);

        // 변경된 리뷰를 저장합니다.
        reviewRepository.save(targetReview);
    }


}
