package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {


}
