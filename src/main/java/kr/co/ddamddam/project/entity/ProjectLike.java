package kr.co.ddamddam.project.entity;

import kr.co.ddamddam.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"user","project"})
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_idx", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_idx", nullable = false)
  private Project project;
}
