package kr.co.ddamddam.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
//TODO: 게시글 간략히 보기 DTO
public class ReviewListResponseDTO {

    private Long reviewIdx;
    private String reviewCompanyName; //회사 이름
    private String reviewTitle;
    private String reviewContent;
    private String reviewJob; //직무
    private Float reviewRating; //별점
    private String reviewLocation; //회사위치
    private int reviewTenure; //경력
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Timestamp reviewDate;
    private int reviewView; //조회수

    @JsonIgnore
    private final int MAX_CONTENT = 5; //최대 글자 수 제한

    //content 글자수 제한하기
    public String getLimitedContent(String reviewContent) {
        if (reviewContent.length() <= MAX_CONTENT) {
            return reviewContent; // 제한 길이 이내인 경우 그대로 반환
        } else {
            return reviewContent.substring(0, MAX_CONTENT) + "..."; // 제한 길이 초과 시 일부만 반환하고 "..." 추가
        }
    }

    public static ReviewListResponseDTO fromReview(Review review) {
        ReviewListResponseDTO dto = new ReviewListResponseDTO();
        dto.setReviewIdx(review.getReviewIdx());
        dto.setReviewCompanyName(review.getCompany().getCompanyName());
        dto.setReviewTitle(review.getReviewTitle());
        dto.setReviewContent(review.getReviewContent());
        dto.setReviewJob(review.getReviewJob());
        dto.setReviewLocation(review.getCompany().getCompanyArea());
        dto.setReviewDate(review.getReviewDate());
        dto.setReviewRating(review.getReviewRating());
        dto.setReviewView(review.getReviewView());

        return dto;
    }


    public ReviewListResponseDTO(Review review) {
//        log.info("review: {}", review);
        this.reviewIdx = review.getReviewIdx();
        this.reviewCompanyName = review.getCompany().getCompanyName();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = getLimitedContent(review.getReviewContent());
        this.reviewJob = review.getReviewJob();
        this.reviewRating = review.getReviewRating();
        this.reviewTenure = review.getReviewTenure();
        this.reviewLocation = review.getCompany().getCompanyArea();
        this.reviewDate = review.getReviewDate();
        this.reviewView = review.getReviewView();
    }
}
