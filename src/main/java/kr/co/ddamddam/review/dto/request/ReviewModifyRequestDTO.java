package kr.co.ddamddam.review.dto.request;

import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;

// TODO: 작성폼 수정 게시판
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModifyRequestDTO {

    @NotNull
    private Long reviewIdx;

    @Size(min = 1, max = 100)
    private String reviewTitle;

    @Size(min = 1, max = 1000)
    private String reviewContent;

    @Min(1)
    private Float reviewRating;

    private String reviewJob;

    private int reviewTenure;

    private String companyName;

    private String reviewLocation;



    public Review toEntity(){

        return Review.builder()
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .reviewRating(this.reviewRating)
                .reviewJob(this.reviewJob)
                .reviewTenure(this.reviewTenure)
                .reviewLocation(this.reviewLocation)
                .reviewCompany(this.companyName)
                .build();
    }

}
