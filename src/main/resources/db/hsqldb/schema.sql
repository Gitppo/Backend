DROP TABLE user IF EXISTS;
CREATE TABLE user
(
`usr_id` INT NOT NULL AUTO_INCREMENT,
`usr_token` VARCHAR(255) NOT NULL,
`usr_name` VARCHAR(10) NOT NULL,
`usr_birth` DATE NOT NULL,
`usr_phone` VARCHAR(20) NOT NULL,

`usr_join_date` DATETIME NOT NULL,

`usr_login_date` DATETIME NOT NULL,
CONSTRAINT PK_user PRIMARY KEY (usr_id)
);

DROP TABLE portfolio IF EXISTS;
CREATE TABLE portfolio
(
`pf_id` INT NOT NULL AUTO_INCREMENT COMMENT '포트폴리오 아이디',
`usr_id` INT NOT NULL COMMENT '유저 아이디',
`pf_name` VARCHAR(50) NOT NULL COMMENT '제목',
`pf_wdate` DATETIME NOT NULL COMMENT '생성일',
`pf_udate` DATETIME NOT NULL COMMENT '수정일',
`pf_template` TINYINT NOT NULL COMMENT '디자인 템플릿 번호',
`pf_grass` TINYINT NOT NULL COMMENT '잔디 노출 여부',
`pf_star` TINYINT NOT NULL COMMENT '스타 노출 여부',
`pf_uuid` VARCHAR(200) NOT NULL COMMENT 'uuid',
`pf_tmp_save` TINYINT NOT NULL DEFAULT 1 COMMENT '임시저장 여부',
CONSTRAINT PK_portfolio PRIMARY KEY (pf_id)
);

ALTER TABLE portfolio
ADD CONSTRAINT FK_portfolio_usr_id_user_usr_id FOREIGN KEY (usr_id)
REFERENCES user (usr_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE repo_group IF EXISTS;
CREATE TABLE repo_group
(
`pf_id` INT NOT NULL AUTO_INCREMENT,
`rp_group` INT NOT NULL,
`rp_grp_name` VARCHAR(50) NOT NULL COMMENT '레포 그룹 이름',
PRIMARY KEY (pf_id, rp_group)
);

ALTER TABLE repo_group
ADD CONSTRAINT FK_repo_group_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE repository IF EXISTS;
CREATE TABLE repository
(
`rp_id` INT NOT NULL AUTO_INCREMENT COMMENT '레포 아이디',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`rp_group` INT NULL COMMENT '그룹 아이디',
`rp_name` VARCHAR(50) NOT NULL COMMENT '레포 이름',
`rp_short_contents` VARCHAR(100) NOT NULL COMMENT '짧은 내용',
`rp_readme` TEXT NULL COMMENT '리드미',
`rp_star` INT NOT NULL DEFAULT 0 COMMENT '스타 개수',
`rp_sdate` DATE NOT NULL COMMENT '시작일o',
`rp_edate` DATE NULL COMMENT '종료일o',
`rp_role` VARCHAR(50) NULL COMMENT '역할o',
`rp_long_contents` TEXT NULL COMMENT '긴 내용o',
`rp_is_readme` TINYINT NOT NULL DEFAULT 1 COMMENT '리드미 포함 여부o',
`rp_is_include` TINYINT NOT NULL DEFAULT 1 COMMENT '포트폴리오 포함 여부o',
`rp_main` TINYINT NOT NULL DEFAULT 0 COMMENT '대표레포 여부o',
CONSTRAINT PK_repository PRIMARY KEY (rp_id)
);

ALTER TABLE repository
ADD CONSTRAINT FK_repository_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE term IF EXISTS;
CREATE TABLE term
(
`term_id` INT NOT NULL AUTO_INCREMENT,
`term_title` VARCHAR(50) NOT NULL COMMENT '약관 제목',
`term_contents` JSON NOT NULL COMMENT '약관 내용',
`term_required` TINYINT NOT NULL DEFAULT 0 COMMENT '약관 필수 여부',
PRIMARY KEY (term_id)
);

DROP TABLE term_agreement IF EXISTS;
CREATE TABLE term_agreement
(
`term_ag_id` INT NOT NULL AUTO_INCREMENT,
`usr_id` INT NOT NULL,
`term_ag_is_agree` TINYINT NOT NULL DEFAULT 1,
`term_ag_date` TIMESTAMP NOT NULL,
`term_id` INT NOT NULL,
PRIMARY KEY (term_ag_id)
);

ALTER TABLE term_agreement
ADD CONSTRAINT FK_term_agreement_usr_id_user_usr_id FOREIGN KEY (usr_id)
REFERENCES user (usr_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE term_agreement
ADD CONSTRAINT FK_term_agreement_term_id_term_term_id FOREIGN KEY (term_id)
REFERENCES term (term_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE rp_skill IF EXISTS;
CREATE TABLE rp_skill
(
`rp_sk_id` INT NOT NULL AUTO_INCREMENT,
`rp_id` INT NOT NULL COMMENT '레포 아이디',
`rp_sk_name` VARCHAR(50) NOT NULL,
`rp_sk_level` TINYINT NOT NULL,
CONSTRAINT PK_rp_skill PRIMARY KEY (rp_sk_id)
);

ALTER TABLE rp_skill
ADD CONSTRAINT FK_rp_skill_rp_id_repository_rp_id FOREIGN KEY (rp_id)
REFERENCES repository (rp_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE rp_language IF EXISTS;
CREATE TABLE rp_language
(
`lg_name` VARCHAR(50) NOT NULL COMMENT '언어 이름',
`rp_id` INT NOT NULL COMMENT '레포 아이디',
`lg_amount` INT NULL,
CONSTRAINT PK_rp_language PRIMARY KEY (lg_name, rp_id)
);

ALTER TABLE rp_language
ADD CONSTRAINT FK_rp_language_rp_id_repository_rp_id FOREIGN KEY (rp_id)
REFERENCES repository (rp_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE paper IF EXISTS;
CREATE TABLE paper
(
`pp_id` INT NOT NULL AUTO_INCREMENT,
`pp_name` VARCHAR(50) NOT NULL COMMENT '이름',
`pp_number` VARCHAR(50) NULL COMMENT '고유번호, 출원번호',
`pp_publisher` VARCHAR(50) NULL COMMENT '출판사, 출원국가',
`pp_writer` VARCHAR(50) NULL COMMENT '저자',
`pp_date` DATE NOT NULL COMMENT '출판일',
`pp_link` VARCHAR(300) NULL COMMENT '링크',
`pp_contents` TEXT NULL COMMENT '내용',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
CONSTRAINT PK_paper PRIMARY KEY (pp_id)
);

ALTER TABLE paper
ADD CONSTRAINT FK_paper_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE skill IF EXISTS;
CREATE TABLE skill
(
`sk_id` INT NOT NULL AUTO_INCREMENT,
`pf_id` INT NOT NULL,
`sk_name` VARCHAR(50) NOT NULL,
`sk_level` TINYINT NOT NULL,
CONSTRAINT PK_skill PRIMARY KEY (sk_id)
);

ALTER TABLE skill
ADD CONSTRAINT FK_skill_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE sns IF EXISTS;
CREATE TABLE sns
(
`sns_id` INT NOT NULL AUTO_INCREMENT,
`sns_name` VARCHAR(50) NULL COMMENT '이름',
`sns_link` VARCHAR(300) NULL COMMENT '링크',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
CONSTRAINT PK_sns PRIMARY KEY (sns_id)
);

ALTER TABLE sns
ADD CONSTRAINT FK_sns_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE education IF EXISTS;
CREATE TABLE education
(
`ed_id` INT NOT NULL AUTO_INCREMENT,
`ed_sdate` DATE NOT NULL COMMENT '시작일',
`ed_edate` DATE NOT NULL COMMENT '종료일',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`ed_name` VARCHAR(50) NOT NULL COMMENT '학교이름',
`ed_major` VARCHAR(50) NOT NULL COMMENT '전공',
`ed_grade` VARCHAR(10) NULL COMMENT '성적',
`ed_submajor` VARCHAR(50) NULL COMMENT '부전공',
`ed_graduation` TINYINT NOT NULL COMMENT '졸업여부(재학중0, 졸업예정1, 졸업2)',
`ed_type` TINYINT NOT NULL COMMENT '고등/대학교/대학원(석사)/대학원(박사)',
CONSTRAINT PK_education PRIMARY KEY (ed_id)
);

ALTER TABLE education
ADD CONSTRAINT FK_education_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE award IF EXISTS;
CREATE TABLE award
(
`aw_id` INT NOT NULL AUTO_INCREMENT,
`aw_name` VARCHAR(50) NOT NULL COMMENT '이름',
`aw_contents` TEXT NOT NULL COMMENT '내용',
`aw_date` DATE NOT NULL COMMENT '수상일',
`aw_org` VARCHAR(50) NOT NULL COMMENT '발급기관',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`pf_link` VARCHAR(300) NULL COMMENT '링크',
`pf_contents` TEXT NULL COMMENT '설명',
CONSTRAINT PK_award PRIMARY KEY (aw_id)
);

ALTER TABLE award
ADD CONSTRAINT FK_award_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE personal IF EXISTS;
CREATE TABLE personal
(
`ps_id` INT NOT NULL AUTO_INCREMENT,
`ps_email` VARCHAR(100) NULL COMMENT '이메일',
`ps_long_intro` TEXT NULL COMMENT '자기소개',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`pf_img` VARCHAR(300) NOT NULL COMMENT '사진',
`ps_short_intro` VARCHAR(100) NULL COMMENT '한줄 자기소개',
CONSTRAINT PK_personal PRIMARY KEY (ps_id)
);

ALTER TABLE personal
ADD CONSTRAINT FK_personal_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE license IF EXISTS;
CREATE TABLE license
(
`lic_id` INT NOT NULL AUTO_INCREMENT,
`lic_date` DATE NOT NULL COMMENT '취득일',
`lic_name` VARCHAR(50) NOT NULL COMMENT '제목',
`lic_contents` TEXT NULL COMMENT '내용',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`lic_org` VARCHAR(50) NOT NULL COMMENT '발급기관',
`lic_level` VARCHAR(50) NOT NULL COMMENT '등급레벨급수',
CONSTRAINT PK_license PRIMARY KEY (lic_id)
);

ALTER TABLE license
ADD CONSTRAINT FK_license_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE career IF EXISTS;
CREATE TABLE career
(
`car_id` INT NOT NULL AUTO_INCREMENT,
`car_sdate` DATE NOT NULL COMMENT '시작일',
`car_edate` DATE NOT NULL COMMENT '종료일',
`car_img` VARCHAR(300) NOT NULL COMMENT '이미지',
`car_name` VARCHAR(50) NOT NULL COMMENT '회사이름',
`car_job` VARCHAR(50) NOT NULL COMMENT '직무',
`car_position` VARCHAR(50) NOT NULL COMMENT '직책',
`car_contents` TEXT NULL COMMENT '내용',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`car_skill` TEXT NULL COMMENT '스택',
`car_link` VARCHAR(300) NULL COMMENT '링크',
CONSTRAINT PK_career PRIMARY KEY (car_id)
);

ALTER TABLE career
ADD CONSTRAINT FK_career_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;

DROP TABLE activity IF EXISTS;
CREATE TABLE activity
(
`act_id` INT NOT NULL AUTO_INCREMENT COMMENT '활동 아이디',
`act_name` VARCHAR(50) NOT NULL COMMENT '활동 제목',
`act_contents` TEXT NOT NULL COMMENT '내용',
`act_img` VARCHAR(300) NOT NULL COMMENT '이미지',
`pf_id` INT NOT NULL COMMENT '포트폴리오 아이디',
`act_sdate` DATE NOT NULL COMMENT '시작일',
`act_edate` DATE NULL COMMENT '종료일',
`act_link` VARCHAR(300) NULL COMMENT '링크',
CONSTRAINT PK_activity PRIMARY KEY (act_id)
);

ALTER TABLE activity
ADD CONSTRAINT FK_activity_pf_id_portfolio_pf_id FOREIGN KEY (pf_id)
REFERENCES portfolio (pf_id) ON DELETE CASCADE ON UPDATE CASCADE;