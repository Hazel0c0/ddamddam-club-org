package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// TODO: 키워드 수정 필요 🍻
public interface CompanyRepository extends JpaRepository<Company, Long> {

    //백엔드 검색
    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE (c.companyTitle LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyArea LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyName LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companySal LIKE CONCAT('%', :keyword, '%')) " +
            "AND (c.companyCareer LIKE CONCAT('%', :keyword2, '%')) " +
            "AND (c.companyTitle LIKE '%백엔드%' OR " +
            "c.companyTitle LIKE '%back%' OR " +
            "c.companyTitle LIKE '%Back%')")
    Page<Company> findByKeywordback(@Param("keyword") String keyword, @Param("keyword2") String keyword2, Pageable pageable);

    //프론트엔드 검색
    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE (c.companyTitle LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyArea LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyName LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companySal LIKE CONCAT('%', :keyword, '%')) " +
            "AND (c.companyCareer LIKE CONCAT('%', :keyword2, '%')) " +
            "AND (c.companyTitle LIKE '%프론트%' OR " +
            "c.companyTitle LIKE '%front%' OR " +
            "c.companyTitle LIKE '%Front%')")
    Page<Company> findByKeywordfront(@Param("keyword") String keyword, @Param("keyword2") String keyword2, Pageable pageable);

    //전체 검색
    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE (c.companyTitle LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyArea LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyName LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companySal LIKE CONCAT('%', :keyword, '%')) " +
            "AND c.companyCareer LIKE CONCAT('%', :keyword2, '%') ")
    Page<Company> findAllBy(@Param("keyword") String keyword, @Param("keyword2") String keyword2, Pageable pageable);


//    @Query("SELECT c FROM Company c WHERE c.companyCareer ='경력' ")
//    Page<Company> findHavingCareer(Pageable pageable);
//
//    @Query("SELECT c FROM Company c WHERE c.companyCareer = '신입'")
//    Page<Company> findCareer(Pageable pageable);
//
//    @Query("SELECT c FROM Company c WHERE c.companyCareer = '관계없음'")
//    Page<Company> findNoExperience(Pageable pageable);


}
