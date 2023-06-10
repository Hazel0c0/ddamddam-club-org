package kr.co.ddamddam.project.entity;

import javax.persistence.Id;

public class User {
    @Id
    private Long userIdx;
    private Long userName;
}
