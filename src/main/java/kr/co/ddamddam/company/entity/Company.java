package kr.co.ddamddam.company.entity;

import kr.co.ddamddam.review.entity.Review;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: 채용공고 게시판
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_idx")
    private Long companyIdx;

    @Column(name = "company_name",nullable = false, length = 30)
    private String companyName;

    @Column(name = "company_title",nullable = false, length = 100)
    private String companyTitle;

//    @Column(name = "company_content",nullable = false, length = 100)
//    private String companyContent;

    @Column(name = "company_career",nullable = false, length = 10 )
    private String companyCareer;

    @Column(name = "company_area",nullable = false, length = 10 )
    private String companyArea; //위치
//    @Column(name = "company_detailadder", length = 10 )
//    private String companyDetailAdder; //상세위치

    @Column(name = "company_url",updatable = false, length = 100)
    private  String companyUrl; // 회사URL

    @Column(name = "company_sal", length = 30)
    private String companySal; //  급여

    @CreationTimestamp
    @Column(name = "company_date",updatable = false)
    private LocalDate companyDate; //작성날짜(채용시작날짜)

    @Column(name = "comapany_enddate",nullable = false)
    private String companyEnddate; //마감날짜 -> api에서 String타입으로 되어있음

    @OneToMany(mappedBy = "company")
    @Builder.Default
    private List<Review> review = new ArrayList<>();
    
}
