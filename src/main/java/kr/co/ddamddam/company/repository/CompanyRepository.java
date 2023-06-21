package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    //키워드 검색
    @Query("SELECT c FROM Company c WHERE c.companyTitle LIKE %:keyword% " +
            "OR c.companyCareer LIKE %:keyword% OR c.companyArea LIKE %:keyword% " +
            "OR c.companyName LIKE %:keyword% OR c.companySal LIKE %:keyword%")
    Page<Company> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


//    @Query("SELECT DISTINCT c FROM Company c")
//    Page<Company> findAllBy(Pageable pageable);


    @Query("SELECT c FROM Company c WHERE c.companyCareer ='경력' ")
    Page<Company> findHavingCareer(Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.companyCareer = '신입'")
    Page<Company> findCareer(Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.companyCareer = '관계없음'")
    Page<Company> findNoExperience(Pageable pageable);









}
