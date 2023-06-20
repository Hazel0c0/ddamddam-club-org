package kr.co.ddamddam.company.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.ddamddam.company.entity.Company;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequestDTO {

    @JsonProperty("company")
    private String companyName;
    @JsonProperty("title")
    private String companyTitle;
    @JsonProperty("career")
    private String companyCareer;
    @JsonProperty("basicAddr")
    private String companyArea;
    @JsonProperty("detailAddr")
    private String companyDetailArea;
    @JsonProperty("wantedInfoUrl")
    private String companyUrl;;
    @JsonProperty("sal")
    private String companySal;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("regDt")
    private String companyDate;
    @JsonProperty("closeDt")
    private String companyEndDate;

    //엔티티로 변환
    public Company toEntity (){

        return Company.builder()
                .companyName(this.companyName)
                .companyTitle(this.companyTitle)
                .companyCareer(this.companyCareer)
                .companyArea(this.companyArea +" "+ this.companyDetailArea)
                .companyUrl(this.companyUrl)
                .companySal(this.companySal)
                .companyDate(this.companyDate)
                .companyEnddate(this.companyEndDate)
                .build();
    }








}
