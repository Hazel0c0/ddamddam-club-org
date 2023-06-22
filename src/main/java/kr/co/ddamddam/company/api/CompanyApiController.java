package kr.co.ddamddam.company.api;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.response.CompanyListPageResponseDTO;
import kr.co.ddamddam.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/companies")
public class CompanyApiController {

    private final CompanyService companyService;

    private String allUrl = "https://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=&callTp=L&region=&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90";


    //전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO) throws IOException {
        companyService.processExternalData(allUrl);
        log.info("api/ddamddam/companies/list?page{}&size={}&sort={}", pageDTO.getPage(), pageDTO.getSize(), pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getList(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    //경력별로 가져오기
    @GetMapping("/career")
    public ResponseEntity<?> listCareer(PageDTO pageDTO) throws IOException {
        log.info("api/ddamddam/companies/career?&page={}&size={}", pageDTO.getPage(), pageDTO.getSize(), pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getCareer(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    //신입
    @GetMapping("/newcareer")
    public ResponseEntity<?> listnewCareer(PageDTO pageDTO) throws IOException {
        log.info("api/ddamddam/companies/newcareer?page={}&size={}", pageDTO.getPage(), pageDTO.getSize(), pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getNewCareer(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    //경력관계없음
    @GetMapping("/nocareer")
    public ResponseEntity<?> listnoCareer(PageDTO pageDTO) throws IOException {
        log.info("api/ddamddam/companies/nocareer?page={}&size={}", pageDTO.getPage(), pageDTO.getSize(), pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getNoCareer(pageDTO);
        return ResponseEntity.ok().body(dto);
    }


    //키워드 검색
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword, PageDTO pageDTO) {
        log.info("api/ddamddam/companies/search?keyword={}&page={}&size={}$sort={}", keyword);
        CompanyListPageResponseDTO companyList = companyService.getKeywordList(keyword, pageDTO);
        return ResponseEntity.ok().body(companyList);
    }


}
