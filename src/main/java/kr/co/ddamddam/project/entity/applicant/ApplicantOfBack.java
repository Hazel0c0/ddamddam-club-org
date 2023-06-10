package kr.co.ddamddam.project.entity.applicant;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString(exclude = {"apply"})
@EqualsAndHashCode(of = {"userIdx"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_applicants")
public class ApplicantOfBack {
  @Id
  private Long userIdx;
}