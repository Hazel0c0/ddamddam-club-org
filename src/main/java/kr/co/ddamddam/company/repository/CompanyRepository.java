package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// TODO: 키워드 수정 필요 🍻
public interface CompanyRepository extends JpaRepository<Company, Long> {


    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "c.companyTitle LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyArea LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyName LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companySal LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:sort IS NULL OR :sort = '' OR c.companyCareer LIKE CONCAT('%', :sort, '%')) " +
            "AND (c.companyTitle LIKE '%백엔드%' OR c.companyTitle LIKE '%back%' OR c.companyTitle LIKE '%Back%') ")
    Page<Company> findByKeyword(@Param("keyword") String keyword, @Param("sort") String sort, Pageable pageable);



    //키워드 검색
//    @Query("SELECT DISTINCT c FROM Company c " +
//            "WHERE ((:keyword IS NULL OR : keyword = '') OR (c.companyTitle LIKE %:keyword% " +
//            "OR c.companyCareer LIKE %:keyword% OR c.companyArea LIKE %:keyword% " +
//            "OR c.companyName LIKE %:keyword% OR c.companySal LIKE %:keyword%)) " +
//            "AND ((:sort IS NULL OR :sort = '') OR c.companyCareer = :sort)")
//    Page<Company> findByKeyword(@Param("keyword") String keyword, @Param("sort") String sort, Pageable pageable);


    @Query("SELECT c FROM Company c WHERE c.companyCareer ='경력' ")
    Page<Company> findHavingCareer(Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.companyCareer = '신입'")
    Page<Company> findCareer(Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.companyCareer = '관계없음'")
    Page<Company> findNoExperience(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE ((:keyword IS NULL OR : keyword = '') OR (c.companyTitle LIKE %:keyword% " +
            "OR c.companyCareer LIKE %:keyword% OR c.companyArea LIKE %:keyword% " +
            "OR c.companyName LIKE %:keyword% OR c.companySal LIKE %:keyword%)) " +
            "AND (c.companyTitle LIKE '%프론트%' OR c.companyTitle LIKE '%front%' OR c.companyTitle LIKE '%Front%')")
    Page<Company> findFront(@Param("keyword") String  keyword,Pageable pageable);

    @Query("SELECT DISTINCT c FROM Company c " +
            "WHERE ((:keyword IS NULL OR : keyword = '') OR (c.companyTitle LIKE %:keyword% " +
            "OR c.companyCareer LIKE %:keyword% OR c.companyArea LIKE %:keyword% " +
            "OR c.companyName LIKE %:keyword% OR c.companySal LIKE %:keyword%)) " +
            "AND (c.companyTitle LIKE '%백엔드%' OR c.companyTitle LIKE '%back%' OR c.companyTitle LIKE '%Back%')")
    Page<Company> findBack(@Param("keyword") String  keyword ,Pageable pageable);


//    @Query("SELECT DISTINCT c FROM Company c " +
//            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
//            "c.companyTitle LIKE CONCAT('%', :keyword, '%') OR " +
//            "c.companyCareer LIKE CONCAT('%', :keyword, '%') OR " +
//            "c.companyArea LIKE CONCAT('%', :keyword, '%') OR " +
//            "c.companyName LIKE CONCAT('%', :keyword, '%') OR " +
//            "c.companySal LIKE CONCAT('%', :keyword, '%')) " +
//            "AND (LOWER(c.companyTitle) LIKE '%백엔드%' OR " +
//            "LOWER(c.companyTitle) LIKE '%back%' OR " +
//            "LOWER(c.companyTitle) LIKE '%Back%')")
//    Page<Company> findBack(@Param("keyword") String keyword, Pageable pageable);

}
