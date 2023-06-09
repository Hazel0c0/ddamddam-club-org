package kr.co.ddamddam.employment.dto.page.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentListResponseDTO {
    private Long boardId;
    private Long employmentCompany;
    private String bordTitle;
    private Float boardGrade;
    private String boardJob;
    private int employmentTenure;

    private String employmentLocation;

    private Timestamp employmentDate;

    private int employmentView;

    private int employmentLike;

    private User user;


}
