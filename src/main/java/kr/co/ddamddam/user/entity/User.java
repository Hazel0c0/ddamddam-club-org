package kr.co.ddamddam.user.entity;

import kr.co.ddamddam.mentor.entity.Mentee;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.ProjectLike;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.review.entity.Review;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


@Setter
@Getter
@ToString(exclude = {"mentor", "mentee", "qna", "review", "projects"})
@EqualsAndHashCode(of = "userIdx")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx; // 식별번호

    @Column(name = "user_email", unique = true, nullable = false, length = 500)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 200)
    private String userPassword;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "user_nickname", unique = true, nullable = false, length = 10)
    private String userNickname;

    @CreationTimestamp // 데이터가 추가되는 시간을 값으로 설정합니다.
    @Column(name = "user_regdate", nullable = false)
    private LocalDateTime userRegdate;

    @Column(name = "user_birth", nullable = false)
    private LocalDate userBirth;

    @Column(name = "user_position", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserPosition userPosition;

    @Column(name = "user_career", nullable = false)
    private int userCareer; // n년

    @Column(name = "user_point")
    @Builder.Default // builder 를 사용했기 때문에
    private Long userPoint = 0L;

    @Column(name = "user_profile", length = 200)
    private String userProfile;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole userRole = UserRole.COMMON;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Mentor> mentor = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Mentee> mentee = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Qna> qna = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
}

