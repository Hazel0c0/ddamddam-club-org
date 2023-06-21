package kr.co.ddamddam.review.entity;

import kr.co.ddamddam.company.entity.Company;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

// TODO : 회사취업후기 게시판
@Setter
@Getter
@ToString(exclude = {"user"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "review_idx")
    private Long reviewIdx;

    @Column(name = "review_title",nullable = false, length = 300)
    private String reviewTitle;

    @Column(name = "review_company",nullable = false, length = 50)
    private String reviewCompany;

    @Column(name = "review_Location",nullable = false, length = 50)
    private String reviewLocation;

    @Column(name ="review_content", nullable = false , length = 3000)
    private String reviewContent;

    @Column(name ="review_rating", nullable = false, columnDefinition = "FLOAT(4,2)")
    private Float  reviewRating; //별점

    @Column(name = "review_job",nullable = false,length = 30)
    private String reviewJob;

    @Column(name = "review_tenure", nullable = false, columnDefinition = "INT(3)")
    private int reviewTenure; //경력

//    @Column(name = "review_location",nullable = false,length = 30)
//    private String reviewLocation; //회사 위치

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime reviewDate;

    @Column(name = "review_view")
    @Builder.Default
    private int reviewView = 0; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

}
