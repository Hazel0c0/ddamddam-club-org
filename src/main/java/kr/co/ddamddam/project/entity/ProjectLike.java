package kr.co.ddamddam.project.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"projectLikeIdx"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_project_like")
public class ProjectLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long projectLikeIdx;

  @Column(nullable = false)
  private Long project_idx;

  @Column(nullable = false)
  private Long memberIdx;
}
