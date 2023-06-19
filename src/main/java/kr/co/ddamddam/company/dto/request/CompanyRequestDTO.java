package kr.co.ddamddam.company.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.company.entity.Company;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequestDTO {
    private String company;
    private String title;
    private String career;
    private String basicAddr;
    private String detailAddr;
    private String wantedInfoUrl;;
    private String sal;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private String regDt;
    private String closeDt;

    //엔티티로 변환
    public Company toEntity (){

        return Company.builder()
                .companyName(this.company)
                .companyTitle(this.title)
                .companyCareer(this.career)
                .companyArea(this.basicAddr +" "+ this.detailAddr)
                .companyUrl(this.wantedInfoUrl)
                .companySal(this.sal)
                .companyDate(LocalDate.parse(this.regDt))
                .companyEnddate(this.closeDt)
                .build();
    }








}
