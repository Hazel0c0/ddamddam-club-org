package kr.co.ddamddam.company.api;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.response.CompanyDetailResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
import kr.co.ddamddam.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/companies")
public class CompanyApiController {
    //리소스: 게시물 (Companies)
    /*
        게시물 목록 조회: /companies       - GET
    */
    private final CompanyService companyService;

    //채용공고게시판 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(PageDTO pageDTO, @RequestParam(required = false) List<String> keyword){
        log.info("api/ddamddam/companies/list?page{}&size={}&sort={}",pageDTO.getPage(),pageDTO.getSize(),pageDTO.getSort());
        CompanyListResponseDTO dto = companyService.getList(pageDTO,keyword);
        return ResponseEntity.ok().body(dto);
    }

    //채용공고게시판 상세 페이지 조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(Long companyIdx){
        log.info("/api/companies/detail?companyIdx={}",companyIdx);
        CompanyDetailResponseDTO dto = companyService.getDetail(companyIdx);
        return ResponseEntity.ok().body(dto);
    }
}
