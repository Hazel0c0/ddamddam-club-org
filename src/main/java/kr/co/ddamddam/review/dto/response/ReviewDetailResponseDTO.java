package kr.co.ddamddam.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
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

    private Company reviewCompany;
    private String reviewTitle;
    private String reviewContent;
    private String reviewJob;
    private Float reviewRating; //별점
    private String reviewLocation;
   @JsonFormat(pattern = "yyyy/MM/dd")
    private Timestamp reviewDate;
    private int reviewView;

    public ReviewDetailResponseDTO(Review review){
        this.reviewCompany = review.getReviewCompany();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.reviewJob = review.getReviewJob();
        this.reviewRating = review.getReviewRating();
        this.reviewLocation = review.getReviewLocation();
        this.reviewDate = review.getReviewDate();
        this.reviewView = review.getReviewView();
    }






}
