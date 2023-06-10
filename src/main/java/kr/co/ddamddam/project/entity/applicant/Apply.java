package kr.co.ddamddam.project.entity.applicant;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"applicants"})
@EqualsAndHashCode(of = {"projectIdx"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_apply")
public class Apply {

    // 프로젝트 게시글
    @Id
    private Long projectIdx;

    @OneToMany(mappedBy = "apply", orphanRemoval = true)
    // 모집 된 인원 정보
    private List<ApplicantOfFront> applicantOfFronts = new ArrayList<>();
    @OneToMany(mappedBy = "apply", orphanRemoval = true)
    private List<ApplicantOfBack> applicantOfBacks = new ArrayList<>();
}
