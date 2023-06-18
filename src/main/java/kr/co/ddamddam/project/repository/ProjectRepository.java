package kr.co.ddamddam.project.repository;


import kr.co.ddamddam.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p " +
      "WHERE p.maxFront - SIZE(p.applicantOfFronts) <> 0")
  Page<Project> findByFrontNotZero(Pageable pageable);

  @Query("SELECT p FROM Project p " +
      "WHERE p.maxBack - SIZE(p.applicantOfBacks) <> 0 ")
  Page<Project> findByBackNotZero(Pageable pageable);


  @Query("SELECT p FROM Project p WHERE LOWER(p.projectTitle) LIKE %:keyword%")
  Page<Project> findProjectsBySearchWord(Pageable pageable, String keyword);
}
