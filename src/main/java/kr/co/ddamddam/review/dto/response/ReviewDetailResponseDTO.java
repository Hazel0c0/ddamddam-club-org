package kr.co.ddamddam.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponseDTO {

    private Long reviewIdx;
    private String reviewCompanyName; //회사 이름
    private String reviewTitle;
    private String reviewContent;
    private String reviewJob; //직무
    private Float reviewRating; //별점
    private String reviewLocation; //회사위치
    private int reviewTenure; //경력
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime reviewDate;
    private int reviewView; //조회수


    public ReviewDetailResponseDTO(Review review) {
        this.reviewIdx = review.getReviewIdx();
        this.reviewCompanyName = review.getReviewCompany();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.reviewJob = review.getReviewJob();
        this.reviewRating = review.getReviewRating();
        this.reviewLocation = review.getReviewLocation();
        this.reviewDate = review.getReviewDate();
        this.reviewView = review.getReviewView();
        this.reviewTenure = review.getReviewTenure();
    }
}
