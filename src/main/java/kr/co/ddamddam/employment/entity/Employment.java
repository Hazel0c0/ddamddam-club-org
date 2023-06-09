package kr.co.ddamddam.employment.entity;

import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

// TODO : 회사취업후기 게시판
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_employment")

public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "employment_idx")
    private Long employmentIdx;

    private Long employmentCompany;

    @Column(name = "employment_title",nullable = false, length = 300)
    private String employmentTitle;

    @Column(name ="employment_content", nullable = false , length = 3000)
    private String employmentContent;

    @Column(name ="employment_grade")
    private Float  employmentGrade;

    @Column(name = "employment_job",length = 30)
    private String employmentJob;

    private int employmentTenure; //이건뭐지??

    @Column(name = "employment_location",length = 30)
    private String employmentLocation;

    @UpdateTimestamp //Defualt current_timestamp
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp employmentDate;

    @Column(name = "review_view")
    private int employmentView;

    @Column(name = "review_like")
    private int employmentLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

}
