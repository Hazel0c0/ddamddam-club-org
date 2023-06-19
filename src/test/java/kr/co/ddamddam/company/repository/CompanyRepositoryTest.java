package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Test
    @DisplayName("채용공고 게시판 9개 생성")
    void builkInsert() {
        for (int i = 1; i <10 ; i++) {
            companyRepository.save(
                    Company.builder()
                            .companyName("감귤회사"+i)
                            .companyTitle("감귤컴퍼니로 입사하세요!"+i)
                            .companyCareer("신입")
                            .companyArea("서울특별시"+i)
                            .companyUrl("http://vist.com")
                            .companySal("3000만원"+i)
                            .companyEnddate("2023-08-0"+i)
                            .build()
            );

        }



    }
    
    


}