package kr.co.ddamddam.user.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "useridx")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(unique = true, nullable = false, length = 30)
    private String userid; // 계정명이 아니라 식별코드

    @Column(nullable = false, length = 30)
    private String userPw;

    @Column(unique = true, nullable = false, length = 100)
    private String userEmail;

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(unique = true, nullable = false, length = 10)
    private String userNickname;

//    @UpdateTimestamp //defualt current_timestamp
    @CreationTimestamp
    private LocalDateTime userRegdate;

    @Column(nullable = false)
    private LocalDate userBirth;

    @Column(nullable = false, length = 30)
    private String userPosition;

    @Column(nullable = false, length = 30)
    private String userCareer;

    @Column(length = 10)
    @Builder.Default // builder를 사용했기 때문에
    private int userPoint = 0;

    @Column(length = 200)
    private String userProfile;

}
