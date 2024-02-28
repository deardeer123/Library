-- 책 정보 insert 문법
LOAD DATA LOCAL INFILE 'D:/01-STUDY/insertData_2.csv'
INTO TABLE BOOK
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

-- 24.02.27 BOOK 테이블에 USER_CODE를 FK로 추가
-- 변경 > libraryBook 매퍼의 book_code 학원 가서 수정 (대출반납 테이블 따로 생성)

-- 대출 테이블
CREATE TABLE BOOK_BORROW(
   BR_CODE INT AUTO_INCREMENT PRIMARY KEY
   , BR_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
   , BOOK_CNT INT NOT NULL
   , RETURN_YN VARCHAR(2) NOT NULL DEFAULT 'N' -- N, Y
   , RETURN_DATE VARCHAR(20)
   , USER_CODE INT REFERENCES USERS(USER_CODE)
   , BOOK_CODE VARCHAR(15) REFERENCES BOOK(BOOK_CODE)
);

-- 대출 혹은 반납 프로세스는 3개의 쿼리를 트랜젝션으로
-- 1. 이용자 조회 2. 대출 내역 조회 3. BOOK_INFO에 BOOK_BORROW_AVAILABLE 업데이트

-- 이용자 정보 insert 문

INSERT INTO users(
	USER_CODE
	, USER_ID
	, USER_PW
	, USER_NAME
	, USER_TEL
	, POST_CODE
	, USER_ADDR
	, ADDR_DETAIL
	, GENDER
	, EMAIL
	, IS_ADMIN
) VALUES (
	1
	, 'ver054'
	, '1234'
	, '김봄이'
	, '010-1111-2222'
	, 12345
	, '울산시 남구'
	, '그린아카데미'
	, '여자'
	, '1234@gmail.com'
	, 'Y'
);