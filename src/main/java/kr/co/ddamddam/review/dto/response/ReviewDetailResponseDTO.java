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

    private Company reviewCompany;
    private String reviewTitle;
    private String reviewContent;
    private String reviewJob;
    private Float reviewRating; //별점
    private String reviewLocation; //이렇게 선언해도 되는거임???
   @JsonFormat(pattern = "yyyy/MM/dd")
    private Timestamp reviewDate;
    private int reviewView;

    public ReviewDetailResponseDTO(Review review){
        this.reviewCompany = review.getCompany();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.reviewJob = review.getReviewJob();
        this.reviewRating = review.getReviewRating();
        //이거 맞는거임?????
        this.reviewLocation = review.getCompany().getCompanyArea();
        this.reviewDate = review.getReviewDate();
        this.reviewView = review.getReviewView();
    }






}
