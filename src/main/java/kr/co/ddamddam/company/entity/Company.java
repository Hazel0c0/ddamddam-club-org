package kr.co.ddamddam.company.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private int companyIdx;

    private String companyImg;

    @Column(nullable = false, length = 30)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String companyTitle;

    @Column(nullable = false, length = 10 )
    private String companyCareer;

    @Column(nullable = false, length = 10 )
    private String companyArea;

    @Column(updatable = false, length = 100)
    private  String companyUrl; // 작성시간

    @UpdateTimestamp //Defualt current_timestamp
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp companyDate;
}
