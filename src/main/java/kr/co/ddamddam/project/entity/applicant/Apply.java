//package kr.co.ddamddam.project.entity.applicant;
//
//import kr.co.ddamddam.project.entity.Project;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Setter
//@Getter
//@ToString(exclude = {"project"})
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "tbl_apply")
//public class Apply {
//
//  @Id
//  private Long applyIdx;
//
//  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  @JoinColumn(name = "project_idx")
//  private Project project;
//
//  // 모집 된 인원 정보
//  @OneToMany(mappedBy = "apply", orphanRemoval = true)
//  private List<ApplicantOfFront> applicantOfFronts = new ArrayList<>();
//  @OneToMany(mappedBy = "apply", orphanRemoval = true)
//  private List<ApplicantOfBack> applicantOfBacks = new ArrayList<>();
//
//  public void addFront(ApplicantOfFront front){
//    applicantOfFronts.add(front);
//    if (this != front.getApply()) {
//      System.out.println("프론트가 비었다면");
//      front.setApply(this);
//    }
//  }
//  public void addBack(ApplicantOfBack back){
//    applicantOfBacks.add(back);
//    if (this != back.getApply()) {
//      System.out.println("백이 비었다면");
//
//      back.setApply(this);
//    }
//  }
//}
