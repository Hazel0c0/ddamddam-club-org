package kr.co.ddamddam.review.dto.request;

import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

// TODO: 작성폼 수정 게시판
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModifyRequestDTO {
    @NotBlank
    private Long reviewIdx;

    @Size(min =1, max = 30)
    private String reviewTitle;

    private String reviewContent;

    @Min(1)
    private Float reviewRating;

    @NotBlank
    private String reviewJob;

    @NotBlank
    private int reviewTenure;

    private Company reviewCompany;

    private String companyaName;

    private String reviewLocation;

    @NotNull
    private Timestamp reviewDate;


    public Review toEntity(){
        Company company = new Company();
        company.setCompanyName(this.companyaName);
        company.setCompanyArea(this.reviewLocation);

        return Review.builder()
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .reviewRating(this.reviewRating)
                .reviewJob(this.reviewJob)
                .reviewTenure(this.reviewTenure)
                .company(company)
                .reviewDate(this.reviewDate)
                .build();
    }

}