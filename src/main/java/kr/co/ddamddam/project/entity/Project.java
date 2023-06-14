package kr.co.ddamddam.project.entity;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
//import kr.co.ddamddam.project.entity.applicant.Apply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"applicantOfFronts", "applicantOfBacks"})
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

  // 좋아요
  @Column(nullable = false)
  private int likeCount;

  // 좋아요를 누른 사용자 추적
  @ManyToMany
  @JoinTable(
      name = "tbl_project_like",
      joinColumns = @JoinColumn(name = "user_idx"),
      inverseJoinColumns = @JoinColumn(name = "project_idx")
  )
  @Builder.Default
  private List<ProjectLike> likedProjects = new ArrayList<>();

  // 모집 된 인원 정보
  @OneToMany(mappedBy = "project", orphanRemoval = true)
  @Builder.Default
  private List<ApplicantOfFront> applicantOfFronts = new ArrayList<>();

  @OneToMany(mappedBy = "project", orphanRemoval = true)
  @Builder.Default
  private List<ApplicantOfBack> applicantOfBacks = new ArrayList<>();

  public void addFront(ApplicantOfFront front) {
    for (ApplicantOfFront existingFront : applicantOfFronts) {
      if (existingFront.equals(front)) {
        System.out.println("해당 front는 이미 저장되었습니다.");
        break;
      }
    }
      applicantOfFronts.add(front);
  }

  public void addBack(ApplicantOfBack back) {
    for (ApplicantOfBack existingBack : applicantOfBacks) {
      if (existingBack.equals(back)) {
        System.out.println("해당 back는 이미 저장되었습니다.");
        return;
      }
    }
      applicantOfBacks.add(back);
  }
}