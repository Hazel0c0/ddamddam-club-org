package kr.co.ddamddam.company.api;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.response.CompanyListPageResponseDTO;
import kr.co.ddamddam.company.service.CompanyService;
import kr.co.ddamddam.company.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/companies")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyApiController {

    private final CompanyService companyService;
    private final DataProcessingService dataProcessingService;

    @PostMapping("/schedule-process-data")
    public ResponseEntity<?> scheduleProcessData() {
        return ResponseEntity.ok("성공적으로 저장되었습니다.");
    }

//    private String allUrl = "https://openapi.work.go.kr/opi/opi/opia/wantedApi.do?authKey=WNLIS5RDCEK7WOBRD73GA2VR1HJ&returnType=xml&display=100&callTp=L&region=&occupation=024";


    //전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO) throws IOException {
        log.info("api/ddamddam/companies/list?page{}&size={}&sort={}", pageDTO.getPage(), pageDTO.getSize(), pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getList(pageDTO);
        return ResponseEntity.ok().body(dto);
    }


    //키워드 검색(백)
    @GetMapping("/searchBack")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword,
                                    @RequestParam("career") String  keyword2 ,
                                    PageDTO pageDTO) {
        log.info("api/ddamddam/companies/search?keyword={}&page={}&size={}&keyword2={}",keyword,pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort()) ;
        CompanyListPageResponseDTO companyList = companyService.getKeywordList(keyword,keyword2,pageDTO);
        log.info("responseDTO : {}", companyList);
        return ResponseEntity.ok().body(companyList);
    }

    //키워드 검색(프론트)
    @GetMapping("/searchFront")
    public ResponseEntity<?> searchfront(@RequestParam("keyword") String keyword,
                                         @RequestParam("career") String  keyword2 ,
                                         PageDTO pageDTO) {
        log.info("api/ddamddam/companies/search?keyword={}&page={}&size={}&keyword2={}",keyword,pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort()) ;
        CompanyListPageResponseDTO companyList = companyService.getKeywordListfront(keyword,keyword2,pageDTO);
        log.info("responseDTO : {}", companyList);
        return ResponseEntity.ok().body(companyList);
    }

    //키워드 검색(전체)
    @GetMapping("/searchall")
    public ResponseEntity<?> searchAll(@RequestParam("keyword") String keyword,
                                         @RequestParam("career") String  keyword2 ,
                                         PageDTO pageDTO) {
        log.info("api/ddamddam/companies/search?keyword={}&page={}&size={}&keyword2={}",keyword,pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort()) ;
        CompanyListPageResponseDTO companyList = companyService.getKeywordListAll(keyword,keyword2,pageDTO);
        log.info("responseDTO : {}", companyList);
        return ResponseEntity.ok().body(companyList);
    }


}