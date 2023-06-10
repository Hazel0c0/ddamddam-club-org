package kr.co.ddamddam.mentor.repository;

import kr.co.ddamddam.mentor.entity.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
}
