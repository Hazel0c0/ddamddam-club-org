package kr.co.ddamddam.project.repository;


import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p " +
      "JOIN p.applicantOfBacks ab " +
      "WHERE (p.maxBack - SIZE(p.applicantOfBacks)) > 0 " +
      "AND ab.user.userIdx <> :userIdx AND p.user.userIdx <> :userIdx " +
      "ORDER BY (p.maxBack - SIZE(p.applicantOfBacks)) ASC, " +
      "SIZE(p.applicantOfFronts) DESC ," +
      "(p.maxFront - SIZE(p.applicantOfFronts)) ASC, " +
      "p.offerPeriod ASC, " +
      "p.likeCount DESC")
  Page<Project> backQuickSort(@Param("userIdx") Long userIdx, Pageable pageable);

  @Query("SELECT p FROM Project p " +
      "JOIN p.applicantOfFronts af " +
      "WHERE (p.maxBack - SIZE(p.applicantOfFronts)) > 0 " +
      "AND af.user.userIdx <> :userIdx AND p.user.userIdx <> :userIdx " +
      "ORDER BY (p.maxFront - SIZE(p.applicantOfFronts)) ASC," +
      "SIZE(p.applicantOfBacks) DESC ," +
      "(p.maxBack - SIZE(p.applicantOfBacks)) ASC, " +
      "p.offerPeriod ASC," +
      "p.likeCount DESC")
  Page<Project> frontQuickSort(@Param("userIdx") Long userIdx, Pageable pageable);

  @Query("SELECT p FROM Project p WHERE LOWER(p.projectTitle) LIKE %:keyword% OR LOWER(p.projectContent) LIKE %:keyword%")
  Page<Project> findProjectsBySearchWord(Pageable pageable, String keyword);

  List<Project> findByUserUserIdx(Long userIdx);

}