package kr.co.ddamddam.mentor.repository;

import kr.co.ddamddam.mentor.entity.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    List<Mentee> findByMentorMentorIdx(Long mentorIdx);
}
