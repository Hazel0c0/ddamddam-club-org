package kr.co.ddamddam.project.entity.applicant;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"project"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_front")
public class ApplicantOfFront {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long frontIdx;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "project_idx")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_idx")
  // 유저 객체로 변경 예정
  private User user;
}
