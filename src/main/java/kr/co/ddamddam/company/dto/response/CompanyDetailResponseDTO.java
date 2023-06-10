package kr.co.ddamddam.company.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.entity.Company;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailResponseDTO {

    private String companyName;
    private String companyTitle;
    private String companyContent;
    private String companyCareer;
    private String companyArea;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Timestamp companyDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate companyEnddate;

    public CompanyDetailResponseDTO(Company company){
        this.companyTitle = company.getCompanyTitle();
        this.companyContent = company.getCompanyContent();
        this.companyName = company.getCompanyName();
        this.companyCareer = company.getCompanyCareer();
        this.companyArea = company.getCompanyArea();
        this.companyDate = company.getCompanyDate();
        this.companyEnddate = company.getCompanyEnddate();
    }






}
