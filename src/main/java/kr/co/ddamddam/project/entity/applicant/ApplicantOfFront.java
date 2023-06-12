package kr.co.ddamddam.project.entity.applicant;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"project"})
@EqualsAndHashCode(of = {"userIdx"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_front")
public class ApplicantOfFront {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "project_idx")
  private Project project;

  @Id
  private Long userIdx;


}
