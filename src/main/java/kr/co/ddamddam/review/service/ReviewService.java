package kr.co.ddamddam.review.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.review.dto.page.PageDTO;
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
import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@SuppressWarnings("unchecked")
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final int VIEW_COUNT = 1;
    private final CompanyRepository companyRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ValidateToken validateToken;

    public ReviewListPageResponseDTO getList(PageDTO pageDTO){
        PageRequest pageable = getPageable(pageDTO);

        // 데이터베이스에서 게시글 목록 조회 후 DTO 리스트로 꺼내기
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

    //페이지네이션
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
    public ReviewDetailResponseDTO getDetail(Long reviewIdx, TokenUserInfo tokenUserInfo) throws ReviewNotFoundException {
        validateToken.validateToken(tokenUserInfo);

        if(reviewIdx == null){
            throw new NotFoundBoardException(INVALID_PARAMETER, reviewIdx);
        }
        Review review = reviewRepository.findById(reviewIdx).orElseThrow(
                    () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, reviewIdx);});

        log.info("토큰아 들어갔니?:{}",review);

        // 조회수를 증가시킵니다.
        increaseReviewView(reviewIdx);

        ReviewDetailResponseDTO dto = new ReviewDetailResponseDTO(review);


        User user = review.getUser();
        if(user != null){
            dto.setUserIdx(user.getUserIdx());
        }
        log.info("토큰아 들어갔니?2{}",dto);

        return  dto;
    }

    //TOP3 게시글 가져오기
    public List<ReviewTopListResponseDTO> getListTop3(){
        List<Review> reviewTopList = reviewRepository.findTop3Desc();

        return reviewTopList.stream().map(review -> ReviewTopListResponseDTO.builder()
                .boardIdx(review.getReviewIdx())
                .boardTitle(review.getReviewTitle())
                .boardView(review.getReviewView())
                .companyName(review.getReviewCompany())
                .boardRating(review.getReviewRating())
                .boardJob(review.getReviewJob())
                .boardTenure(review.getReviewTenure())
                .boardLocation(review.getReviewLocation())
                .build()
        ).collect(toList());
    }



    // 조회순으로 조회하기
    public ReviewListPageResponseDTO getListOrderByView(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        Page<Review> reviews = reviewRepository.findByDesc(pageable);

        List<ReviewListResponseDTO> reviewListResponseDTOS = getReviewListView(reviews);

        return ReviewListPageResponseDTO.builder()
                .count(reviewListResponseDTOS.size())
                .pageInfo(new PageResponseDTO(reviews))
                .responseList(reviewListResponseDTOS)
                .build();
    }

    private List<ReviewListResponseDTO> getReviewListView(Page<Review> reviews) {
        return reviews.stream()
                .map(ReviewListResponseDTO::new)
                .collect(toList());
    }


    // 평점순으로 조회하기
    public ReviewListPageResponseDTO getListOrderByRateDesc(PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        Page<Review> reviews = reviewRepository.findByRate(pageable);

        List<ReviewListResponseDTO> reviewListResponseDTOS = getReviewListRate(reviews);

        return ReviewListPageResponseDTO.builder()
                .count(reviewListResponseDTOS.size())
                .pageInfo(new PageResponseDTO(reviews))
                .responseList(reviewListResponseDTOS)
                .build();
    }

    private List<ReviewListResponseDTO> getReviewListRate(Page<Review> reviews) {
        return reviews.stream()
                .map(ReviewListResponseDTO::new)
                .collect(toList());
    }

    //키워드 검색기능 구현하기
    public ReviewListPageResponseDTO getKeywordList(String keyword,String sort,PageDTO pageDTO){

        PageRequest pageable = getPageable(pageDTO);
        Page<Review> reviews = reviewRepository.findByKeyword(keyword,sort,pageable);
        List<ReviewListResponseDTO> reviewListResponseDTOS = getReviewListKeyword(reviews);

        return ReviewListPageResponseDTO.builder()
                .count(reviewListResponseDTOS.size())
                .pageInfo(new PageResponseDTO(reviews))
                .responseList(reviewListResponseDTOS)
                .build();
    }

    private List<ReviewListResponseDTO> getReviewListKeyword(Page<Review> reviews) {
        return reviews.stream()
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



    //게시글 작성
    public ReviewDetailResponseDTO write(ReviewWriteRequestDTO dto, TokenUserInfo tokenUserInfo) throws ReviewNotFoundException {

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundUserException(ErrorCode.NOT_FOUND_USER, userIdx);
        });

        Review saved = reviewRepository.save(dto.toEntity(user));
        ReviewDetailResponseDTO detail = getDetail(saved.getReviewIdx(),tokenUserInfo);
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with id: " + userIdx));

        return detail;
    }

    //수정
    public ReviewDetailResponseDTO modify(ReviewModifyRequestDTO dto ,TokenUserInfo tokenUserInfo) throws RuntimeException, ReviewNotFoundException {

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());


        Review review = reviewRepository.findByReviewIdxAndUserUserIdx(dto.getReviewIdx(),userIdx).orElseThrow(
                () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, dto.getReviewIdx());}
        );

            review.setReviewTitle(dto.getReviewTitle());
            review.setReviewContent(dto.getReviewContent());
            review.setReviewJob(dto.getReviewJob());
            review.setReviewRating(dto.getReviewRating());
            review.setReviewCompany(dto.getCompanyName());
            review.setReviewLocation(dto.getReviewLocation());
            review.setReviewTenure(dto.getReviewTenure());

            reviewRepository.save(review);
        return getDetail(dto.getReviewIdx(),tokenUserInfo);

    }

    //게시판 삭제
    public void delete(Long reviewIdx) throws RuntimeException{
        Review targetReview = reviewRepository.findById(reviewIdx).orElseThrow(
                () -> {throw  new NotFoundBoardException(NOT_FOUND_BOARD, reviewIdx);}
        );
            reviewRepository.delete(targetReview);
        }

    //조회수 상승
//    @Transactional
    public void increaseReviewView(Long reviewIdx) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewIdx).orElseThrow(
                () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, reviewIdx);}
        );

        // 조회수를 1 증가시킵니다.
        review.setReviewView(review.getReviewView() + 1);

        // 변경된 리뷰를 저장합니다.
        reviewRepository.save(review);
    }


}
