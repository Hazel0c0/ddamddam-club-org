package kr.co.ddamddam.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;

import java.sql.Timestamp;

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
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Timestamp reviewDate;
    private int reviewView; //조회수


    public ReviewDetailResponseDTO(Review review) {
        this.reviewIdx = review.getReviewIdx();
        this.reviewCompanyName = review.getCompany().getCompanyName();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.reviewJob = review.getReviewJob();
        this.reviewRating = review.getReviewRating();
        this.reviewLocation = review.getCompany().getCompanyArea(); //이거 맞는거임 ?
        this.reviewDate = review.getReviewDate();
        this.reviewView = review.getReviewView();
        this.reviewTenure = review.getReviewTenure();
    }
}
