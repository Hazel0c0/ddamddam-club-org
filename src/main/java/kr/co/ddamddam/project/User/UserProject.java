package kr.co.ddamddam.project.User;

//import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user_copy")
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx; // 식별번호

    @Column(name = "user_email", unique = true, nullable = false, length = 100)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 30)
    private String userPw;

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
//    @JoinColumn(name = "user")
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
    private UserRole userRole;

//    @OneToMany(mappedBy = "user")
//    private List<Qna> qna;

}

