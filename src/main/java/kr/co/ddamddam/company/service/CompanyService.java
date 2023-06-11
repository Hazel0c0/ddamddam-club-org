package kr.co.ddamddam.company.service;

import kr.co.ddamddam.company.dto.page.PageDTO;
import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyDetailResponseDTO;
import kr.co.ddamddam.company.dto.response.CompanyListResponseDTO;
import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyListResponseDTO getList(PageDTO pageDTO, List<String> keyword) {


        //pageable 객체 생성
        Pageable pageable = (Pageable) PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by(pageDTO.getSort()).descending()
        );

        //채용공고글 목록 조회
        Page<Company> companies= companyRepository.findAll(pageable);

        List<Company> companyList = companies.getContent();
        List<CompanyDetailResponseDTO> companyDetailResponseDTOList = companyList.stream().map(company -> {
            CompanyDetailResponseDTO dto = new CompanyDetailResponseDTO();

            dto.setCompanyTitle(company.getCompanyTitle());
            dto.setCompanyContent(company.getCompanyContent());
            dto.setCompanyName(company.getCompanyName());
            dto.setCompanyCareer(company.getCompanyCareer());
            dto.setCompanyArea(company.getCompanyArea());
            dto.setCompanyDate(company.getCompanyDate());
            dto.setCompanyEnddate(company.getCompanyEnddate());

            return dto;
        }).collect(toList());

        return  CompanyListResponseDTO.builder()
                .count(companyDetailResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Company>(companies))
                .companys(companyDetailResponseDTOList)
                .build();
    }

    //채용공고 게시판 상세 조회
    public CompanyDetailResponseDTO getDetail(Long companyIdx) {

        Optional<Company> mentorOptional = companyRepository.findById(companyIdx);
        Company company = mentorOptional.get();
        CompanyDetailResponseDTO dto = new CompanyDetailResponseDTO();
        dto.setCompanyTitle(company.getCompanyTitle());
        dto.setCompanyContent(company.getCompanyContent());
        dto.setCompanyName(company.getCompanyName());
        dto.setCompanyCareer(company.getCompanyCareer());
        dto.setCompanyArea(company.getCompanyArea());
        dto.setCompanyDate(company.getCompanyDate());
        dto.setCompanyEnddate(company.getCompanyEnddate());

        return dto;
    }






}
