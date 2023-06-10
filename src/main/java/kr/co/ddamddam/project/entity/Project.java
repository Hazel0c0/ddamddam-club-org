package kr.co.ddamddam.project.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
  private int maxFront;
  private int maxBack;

  private String applicantionPeriod; //모집기간

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
}