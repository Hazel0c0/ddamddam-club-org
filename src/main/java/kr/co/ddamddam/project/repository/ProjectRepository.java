package kr.co.ddamddam.project.repository;


import kr.co.ddamddam.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p " +
      "WHERE (p.maxBack - SIZE(p.applicantOfBacks)) > 0 " +
      "AND p.projectIdx NOT IN (SELECT a.project.projectIdx FROM ApplicantOfBack a WHERE a.user.userIdx = :userIdx) " +
      "AND p.user.userIdx <> :userIdx " +
      "ORDER BY (p.maxBack - SIZE(p.applicantOfBacks)) ASC, " +
      "SIZE(p.applicantOfFronts) DESC, " +
      "SIZE(p.applicantOfBacks) DESC, " +
      "p.offerPeriod ASC, " +
      "p.likeCount DESC")
  Page<Project> backQuickMatchingSort(@Param("userIdx") Long userIdx, Pageable pageable);

  @Query("SELECT p FROM Project p " +
      "WHERE (p.maxFront - SIZE(p.applicantOfFronts)) > 0 " +
      "AND p.projectIdx NOT IN (SELECT a.project.projectIdx FROM ApplicantOfFront a WHERE a.user.userIdx = :userIdx) " +
      "AND p.user.userIdx <> :userIdx " +
      "ORDER BY (p.maxFront - SIZE(p.applicantOfFronts)) ASC, " +
      "SIZE(p.applicantOfBacks) DESC, " +
      "SIZE(p.applicantOfFronts) DESC, " +
      "p.offerPeriod ASC, " +
      "p.likeCount DESC")
  Page<Project> frontQuickMatchingSort(@Param("userIdx") Long userIdx, Pageable pageable);

  @Query("SELECT p FROM Project p WHERE LOWER(p.projectTitle) LIKE %:keyword% OR LOWER(p.projectContent) LIKE %:keyword%")
  Page<Project> findProjectsByKeyword(Pageable pageable, @Param("keyword")String keyword);

  List<Project> findByUserUserIdx(Long userIdx);

  @Modifying
  @Query("DELETE FROM Project p WHERE p.projectIdx = :projectIdx")
  void deleteByIdx(@Param("projectIdx")Long projectIdx);


}