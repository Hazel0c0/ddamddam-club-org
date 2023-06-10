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
@Transactional
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
                            .companyArea("서울특별시"+i)
                            .companyContent("Springboot를 이용한 web개발 경험이 있으신분"+i)
                            .companyCareer("3")
                            .companyEnddate(LocalDate.parse("2023-08-"+i))
                            .build()
            );

        }



    }
    
    


}