package kr.co.ddamddam.company.dto.response;

import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.dto.request.CompanyRequestDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListResponseDTO {
    private int count;
    private PageResponseDTO pageInfo;
    private List<CompanyRequestDTO> companys;

}
