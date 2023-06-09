package kr.co.ddamddam.company.service;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.response.CompanyDetailResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;


    public CompanyListResponseDTO getList(PageDTO pageDTO) {
        //pageable 객체 생성
        Pageable pageable = (Pageable) PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by(pageDTO.getSort()).descending()
        );

        //채용공고 목록 조회
        Page<Company> companies = companyRepository.findAll(pageable);


        List<CompanyDetailResponseDTO> detailList
                = companies.stream()
    }


}
