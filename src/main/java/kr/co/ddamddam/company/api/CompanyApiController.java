package kr.co.ddamddam.company.api;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.response.CompanyDetailResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListPageResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
//import kr.co.ddamddam.company.service.CompanyService;
import kr.co.ddamddam.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/companies")
public class CompanyApiController {

    private final CompanyService companyService;

    //전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO) throws IOException {
        companyService.processExternalData();
        log.info("api/ddamddam/companies/list>page{}&size={}&sort={}",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        CompanyListPageResponseDTO dto = companyService.getList(pageDTO);
        return ResponseEntity.ok().body(dto);
    }

    //키워드 검색
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword) {
        log.info("api/ddamddam/companies/search?keyword={}",keyword);
        CompanyListPageResponseDTO companyList = companyService.getKeywordList(keyword);
        return ResponseEntity.ok().body(companyList);
    }






}
