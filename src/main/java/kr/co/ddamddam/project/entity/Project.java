package kr.co.ddamddam.project.entity;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
//import kr.co.ddamddam.project.entity.applicant.Apply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_idx")
  private User user;

  private String userNickname;

  @Column(nullable = false, length = 100)
  private String projectTitle;

  @Column(nullable = true)
  private String projectImg;

  @Column(nullable = false, length = 3000)
  private String projectContent;

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

  // 좋아요
  @Column(nullable = false)
  private int likeCount;

  // 좋아요를 누른 사용자 추적
  @OneToMany(mappedBy = "project")
  @Builder.Default
  private List<ProjectLike> likes = new ArrayList<>();

  // 모집 된 인원 정보
  @OneToMany(mappedBy = "project", orphanRemoval = true)
  @Builder.Default
  private List<ApplicantOfFront> applicantOfFronts = new ArrayList<>();

  // 모집 된 백엔드 인원 정보
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