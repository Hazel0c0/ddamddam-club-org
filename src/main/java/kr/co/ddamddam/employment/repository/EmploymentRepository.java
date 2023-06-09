package kr.co.ddamddam.employment.repository;

import kr.co.ddamddam.employment.entity.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface EmploymentRepository extends JpaRepository<Employment,Long> {

}
