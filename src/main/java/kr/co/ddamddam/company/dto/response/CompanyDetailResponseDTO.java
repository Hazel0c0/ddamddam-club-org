package kr.co.ddamddam.company.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.entity.Company;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailResponseDTO {

    private Long companyIdx;
    private String companyName;
    private String companyTitle;
    private String companyCareer;
    private String companyArea;
    private String companyUrl;;
    private String companySal;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private String companyDate;
    private String companyEnddate;

    public CompanyDetailResponseDTO(Company company){
        this.companyIdx = company.getCompanyIdx();
        this.companyName = company.getCompanyName();
        this.companyTitle = company.getCompanyTitle();
        this.companyCareer = company.getCompanyCareer();
        this.companyArea = company.getCompanyArea();
        this.companyUrl = company.getCompanyUrl();
        this.companySal = company.getCompanySal();
        this.companyDate = company.getCompanyDate();
        this.companyEnddate = company.getCompanyEnddate();
    }

}
