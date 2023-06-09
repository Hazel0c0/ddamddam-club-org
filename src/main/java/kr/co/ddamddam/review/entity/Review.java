package kr.co.ddamddam.review.entity;

import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// TODO : 회사취업후기 게시판
@Setter
@Getter
@ToString
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

    @Column(name = "review_company",length = 30)
    private Long reviewCompany;

    @Column(name = "review_title",nullable = false, length = 300)
    private String reviewTitle;

    @Column(name ="review_content", nullable = false , length = 3000)
    private String reviewContent;

    @Column(name ="review_grade", columnDefinition = "FLOAT(1,3)")
    private Float  reviewGrade;

    @Column(name = "review_job",length = 30)
    private String reviewJob;

    @Column(name = "review_rating", columnDefinition = "int(3)")
    private int reviewRating; //별점

    @Column(name = "review_location",length = 30)
    private String reviewLocation;

    @UpdateTimestamp //Defualt current_timestamp
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp reviewDate;

    @Column(name = "review_view")
    private int reviewView;

    @Column(name = "review_like")
    private int reviewLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

}
