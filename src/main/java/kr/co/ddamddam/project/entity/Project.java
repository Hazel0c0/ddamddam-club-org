package kr.co.ddamddam.project.entity;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
//import kr.co.ddamddam.project.entity.applicant.Apply;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString
@EqualsAndHashCode(of = {"projectIdx"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_project")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long projectIdx;

  @Column(nullable = false, length = 100)
  private String projectTitle;

  @Column(nullable = false, length = 3000)
  private String projectContent;

  // 작성자 FK
  private String writer;

  @Column(nullable = false, length = 10)
  private String projectType;

  //모집인원
  @Column(nullable = false)
  private int maxFront;
  @Column(nullable = false)
  private int maxBack;

  private String offerPeriod; //모집기간

  @CreationTimestamp
  @Column(
      nullable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      length = 30
  )
  private LocalDateTime projectDate;

  // FK
//  @Column(nullable = false)
  private String memberIdx;

  // 모집 된 인원 정보
  @OneToMany(mappedBy = "project", orphanRemoval = true)
  @Builder.Default
  private List<ApplicantOfFront> applicantOfFronts = new ArrayList<>();

  @OneToMany(mappedBy = "project", orphanRemoval = true)
  @Builder.Default
  private List<ApplicantOfBack> applicantOfBacks = new ArrayList<>();

  public void addFront(ApplicantOfFront front){
    applicantOfFronts.add(front);
    if (this != front.getProject()) {
      System.out.println("프론트가 비었다면");
      front.setProject(this);
    }
  }
  public void addBack(ApplicantOfBack back){
    applicantOfBacks.add(back);
    if (this != back.getProject()) {
      System.out.println("백이 비었다면");

      back.setProject(this);
    }
  }
}