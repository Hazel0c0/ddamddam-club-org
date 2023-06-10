use
    ddamddam;

CREATE TABLE tbl_user_copy
(
    user_idx      INT AUTO_INCREMENT PRIMARY KEY,
    user_email    VARCHAR(100) UNIQUE NOT NULL,
    user_password VARCHAR(30)         NOT NULL,
    user_name     VARCHAR(30)         NOT NULL,
    user_nickname VARCHAR(10) UNIQUE  NOT NULL,
    user_regdate  TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_birth    DATE                NOT NULL,
    user_position VARCHAR(30)         NOT NULL,
    user_career   INT                 NOT NULL,
    user_point    BIGINT                       DEFAULT 0,
    user_profile  VARCHAR(200),
    user_role     VARCHAR(255)        NOT NULL
);

INSERT INTO tbl_user_copy (user_email, user_password, user_name, user_nickname, user_regdate, user_birth, user_position,
                           user_career, user_point, user_profile, user_role)
VALUES ('user1@example.com', 'password1', 'John Doe', 'johndoe', NOW(), '1990-01-01', 'BACKEND', 5, 100, 'profile1.jpg',
        'USER'),
       ('user2@example.com', 'password2', 'Jane Smith', 'janesmith', NOW(), '1995-03-15', 'FRONTEND', 3, 50,
        'profile2.jpg', 'USER');

CREATE TABLE tbl_applicant
(
    project_idx BIGINT PRIMARY KEY
);

drop table tbl_applicant;

CREATE TABLE tbl_applicant_of_front
(
    applicant_project_idx BIGINT,
    applicant_user_idx    BIGINT,
    FOREIGN KEY (applicant_project_idx) REFERENCES tbl_applicant (project_idx),
    FOREIGN KEY (applicant_user_idx) REFERENCES tbl_user (user_idx)
);

CREATE TABLE tbl_applicant_of_back
(
    applicant_project_idx BIGINT,
    applicant_user_idx    BIGINT,
    FOREIGN KEY (applicant_project_idx) REFERENCES tbl_applicant (project_idx),
    FOREIGN KEY (applicant_user_idx) REFERENCES tbl_user (user_idx)
);