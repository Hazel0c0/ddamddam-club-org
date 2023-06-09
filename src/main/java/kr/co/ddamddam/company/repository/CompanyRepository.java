package kr.co.ddamddam.company.repository;

import kr.co.ddamddam.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    //integer랑 String중에 어떤걸로 해야함??

}
