package kr.co.ddamddam.mentor.repository;

import kr.co.ddamddam.mentor.entity.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    @Query("SELECT m FROM Mentor m WHERE LOWER(m.mentorSubject) IN :subjects")
    Page<Mentor> findByMentorSubjectInIgnoreCase(List<String> subjects, Pageable pageable);

    List<Mentor> findByUserUserIdx(Long userIdx);
}
