package kr.co.ddamddam.company.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import kr.co.ddamddam.company.entity.Company;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListResponseDTO {
    private Long companyIdx;
    private String companyName;
    private String companyTitle;
    private String companyCareer;
    private String companyArea;
    private String companyUrl;;
    private String companySal;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate companyDate;
    private String companyEnddate;

    public CompanyListResponseDTO(Company company){
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
