package kr.co.ddamddam.mentor.repository;

import kr.co.ddamddam.mentor.entity.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    @Query("SELECT m FROM Mentor m WHERE LOWER(m.mentorSubject) IN :subjects")
    Page<Mentor> findByMentorSubjectInIgnoreCase(List<String> subjects, Pageable pageable);
    Optional<Mentor> findByMentorIdxAndUserUserIdx(Long mentorIdx, Long userIdx);

    List<Mentor> findByUserUserIdx(Long userIdx);
}
