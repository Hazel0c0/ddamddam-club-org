package kr.co.ddamddam.project.repository;


import kr.co.ddamddam.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p " +
        "WHERE (p.maxBack - SIZE(p.applicantOfBacks)) > 0 " +
        "ORDER BY (p.maxBack - SIZE(p.applicantOfBacks)) ASC, p.projectDate ASC")
    Page<Project> backQuickSort(Pageable pageable);

    @Query("SELECT p FROM Project p " +
        "WHERE (p.maxFront - SIZE(p.applicantOfFronts)) > 0 " +
        "ORDER BY (p.maxFront - SIZE(p.applicantOfFronts)) ASC, p.projectDate ASC")
    Page<Project> frontQuickSort(Pageable pageable);

    @Query("SELECT p FROM Project p WHERE LOWER(p.projectTitle) LIKE %:keyword%")
    Page<Project> findProjectsBySearchWord(Pageable pageable, String keyword);

    List<Project> findByUserUserIdx(Long userIdx);

}
