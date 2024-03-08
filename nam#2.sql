CREATE TABLE USERS(
	USER_CODE INT AUTO_INCREMENT PRIMARY KEY
	, USER_ID VARCHAR(20) NOT NULL
	, USER_PW VARCHAR(50) NOT NULL 
	, USER_NAME VARCHAR(20) NOT NULL
	, USER_TEL VARCHAR(20) NOT NULL
	, POST_CODE INT NOT NULL
	, USER_ADDR VARCHAR(50) NOT NULL
	, ADDR_DETAIL VARCHAR(50) NOT NULL
	, GENDER VARCHAR(2) NOT NULL
	, EMAIL VARCHAR(50) NOT NULL
	, IS_ADMIN VARCHAR(2) DEFAULT 'N'); -- N,Y

ALTER TABLE USERS ADD COLUMN CARD_NUM INT;
ALTER TABLE USERS ADD COLUMN PUBLISH_DATE DATETIME;
ALTER TABLE USERS ADD COLUMN USER_INTRO VARCHAR(200);
ALTER TABLE USERS ADD COLUMN CARD_STATUS VARCHAR(10) DEFAULT '사용중';

ALTER TABLE USERS CHANGE USER_PW USER_PW VARCHAR(100) NOT NULL;
ALTER TABLE USERS CHANGE CARD_NUM CARD_NUM INT;
	
	
DROP TABLE users;


CREATE TABLE HEADER_MENU(
	MENU_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	MENU_NAME VARCHAR(20) NOT NULL ,
	MENU_PAGE VARCHAR(50) NOT NULL ,
	MENU_INDEX INT NOT NULL ,
	MENU_TYPE VARCHAR(20) NOT NULL
);

-- 갈아엎기 
-- DROP TABLE header_menu;

CREATE TABLE SIDE_MENU(
	SIDE_MENU_NUM INT AUTO_INCREMENT PRIMARY KEY,
	SIDE_MENU_NAME VARCHAR(20) NOT NULL ,
	SIDE_MENU_PAGE VARCHAR(50) NOT NULL ,
	SIDE_MENU_INDEX INT NOT NULL ,
	MENU_NUM INT REFERENCES HEADER_MENU(menu_num));

-- 갈아엎기
-- DROP TABLE side_menu;

-- 헤더 파일 데이터 도서 관리쪽임
INSERT INTO HEADER_MENU(
	MENU_NAME , MENU_PAGE , MENU_INDEX , MENU_TYPE)
	VALUES
	('대출 반납', 'borrowReturn', 1 , 'library'),
	('이용자', 'user', 2 , 'library') ,
	('구입', 'buy', 3 , 'library') ,
	('등록 열람', 'regAndView', 4 , 'library') ,
	('통계', 'statistics', 5 , 'library');
	
-- 헤더 파일 데이터 도서관 웹쪽임
INSERT INTO HEADER_MENU(
	MENU_NAME , MENU_PAGE , MENU_INDEX , MENU_TYPE)
	VALUES
	('자료찾기', 'findBook', 1 , 'web'),
	('도서관 이용', 'libraryUse', 2 , 'web') ,
	('문화행사/교육', 'culturalAndEducation', 3 , 'web') ,
	('참여마당', 'forum', 4 , 'web') ,
	('도서관 소개', 'libraryintroduction', 5 , 'web');

-- 옆에 사이드 메뉴에 띄울려면 넣기는 넣어야할듯 (로그인 / 회원가입 / 아이디,비밀번호 찾기)
INSERT INTO HEADER_MENU(
	MENU_NAME , MENU_PAGE , MENU_INDEX , MENU_TYPE)
	VALUES
	('회원', 'member', 1 , 'member');

SELECT * FROM header_menu;
SELECT * FROM side_menu;

-- 사이드 메뉴 데이터
INSERT INTO SIDE_MENU(
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	MENU_NUM)
	VALUES 
	-- 대출 반납
	('대출 반납', 'borrowReturn', 1 , 1 ),
	('일관 반납', 'consistentReturn', 2 , 1 ),
	('대출 반납 관리', 'borrowReturnManagement', 3 , 1),
	('예약정보 관리', 'reservationInfo', 4 , 1),
	('출력이력 관리', 'outputHistory', 5 , 1) ,
	-- 이용자
	('이용자 관리', 'user' , 1 , 2) ,
	('이용자 승인', 'userApproval', 2 , 2) ,
	('연체자 관리', 'delinquent', 3 , 2) ,
	-- 구입
	('희망 자료' , 'wishBook' , 1 , 3 ) ,
	('삭제 자료' , 'deleteBook', 2 , 3 ) ,
	('구입 자료' , 'buyBook', 3 , 3 ) ,
	('기증 자료' , 'donatedBook', 4 , 3 ) ,
	-- 등록 열람
	('작업 자료 관리', 'workingBook', 1, 4 ),
	('소장 자료 관리', 'collectionBook', 2, 4 ),
	('마크 반입', 'markImport', 3, 4  ),
	-- 통계
	('통계' , 'statistics', 1, 5 );
	
-- 사이드 메뉴 (웹)
INSERT INTO side_menu(
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	MENU_NUM )
	VALUES
	-- 자료찾기
	('전체자료찾기', 'findFullBook', 1 , 6 ) ,
	('새로 들어온 책', 'newBook', 2 , 6 ) ,
	('추천도서', 'recommendedBook', 3 , 6 ) ,
	('대출이 많은책', 'manyBorrowedBook' , 4 , 6 ) ,
	('희망도서신청', 'hopeBookApplication' , 5 , 6),
	-- 도서관 이용
	('이용안내 및 자료실 소개', 'userGuide', 1 , 7) ,
	('이달의 행사 및 휴관일', 'eventAndCloseDay' , 2 , 7) ,
	-- 문화행사 / 교육
	('도서관 행사', 'libraryEvent', 1 , 8) ,
	('행사 참가신청', 'eventParticipation' , 2 , 8) ,
	('영화 상영', 'movie', 3 , 8 ) ,
	('평생교육 강좌안내', 'courseGuide', 4, 8) ,
	('강좌 수강신청', 'applicationForClasses' , 5 , 8 ) ,
	-- 참여 마당
	('공지사항', 'notice', 1 , 9 ) ,
	('묻고 답하기', 'askAndAnswer', 2 , 9) ,
	('자료기증', 'bookDonation' , 3 , 9 ) ,
	('사물함 예약', 'lockerReservation' , 4, 9) ,
	-- 도서관 소개
	('인사말' , 'greeting' , 1 , 10 ) ,
	('연혁' , 'history' , 2 , 10 ) ,
	('도서관 현황', 'libraryStatus', 3 , 10) ,
	('찾아오시는 길', 'libraryCome', 4, 10) ; 
	
-- 헤더 윗 부분(로그인/회원가입/아이디,비밀번호 찾기)를 위한 데이터
-- 회원 탈퇴 부분은 마이페이지이런곳에 들어가서 할 수 있도록 만들어야 할듯
INSERT INTO side_menu(
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	MENU_NUM )
	VALUES
	('로그인', 'login', 1 , 11),
	('회원가입', 'join', 2 , 11),
	('아이디/비밀번호 찾기', 'findIdOrPW', 3, 11);
	
-- 메뉴들 찾기 위한 쿼리문
SELECT 
	header_menu.MENU_NUM ,
	MENU_NAME ,
	MENU_PAGE ,
	MENU_INDEX ,
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	SIDE_MENU.SIDE_MENU_NUM
FROM
	header_menu INNER JOIN side_menu
	ON header_menu.MENU_NUM = side_menu.MENU_NUM 
WHERE
	header_menu.MENU_TYPE = 'library';
	
SELECT 
	header_menu.MENU_NUM ,
	MENU_NAME ,
	MENU_PAGE ,
	MENU_INDEX ,
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	SIDE_MENU.SIDE_MENU_NUM
FROM
	header_menu INNER JOIN side_menu
	ON header_menu.MENU_NUM = side_menu.MENU_NUM 
WHERE
	header_menu.MENU_TYPE = 'web';
	
SELECT 
	header_menu.MENU_NUM ,
	MENU_NAME ,
	MENU_PAGE ,
	MENU_INDEX ,
	SIDE_MENU_NAME ,
	SIDE_MENU_PAGE ,
	SIDE_MENU_INDEX ,
	SIDE_MENU.SIDE_MENU_NUM
FROM
	header_menu INNER JOIN side_menu
	ON header_menu.MENU_NUM = side_menu.MENU_NUM 
WHERE
	header_menu.MENU_TYPE = 'member';
	
-- culturalAndEducation
-- findBook
-- home
-- libraryIntroduction
-- member
-- participationForum
	
	
CREATE TABLE BOOK(
   BOOK_CODE VARCHAR(15) PRIMARY KEY,
   BOOK_TITLE VARCHAR(100) NOT NULL,
   BOOK_WRITER VARCHAR(100) NOT NULL,
   BOOK_PUB VARCHAR(30) NOT NULL,
   BOOK_YEAR VARCHAR(20) NOT NULL);

CREATE TABLE BOOK_CATEGORY(
	BOOK_CATE_CODE INT AUTO_INCREMENT PRIMARY KEY,
	BOOK_CATE_NAME VARCHAR(20) NOT NULL ,
	BOOK_CATE_INDEX INT NOT NULL
);

INSERT INTO book_category(
	BOOK_CATE_NAME ,
	BOOK_CATE_INDEX)
	VALUES
	('총류' , 0) ,
	('철학' , 1) ,
	('종교' , 2) ,
	('사회과학' , 3) ,
	('자연과학' , 4) ,
	('기술과학' , 5) ,
	('예술' , 6 ) ,
	('언어' , 7 ) ,
	('문학' , 8 ) ,
	('역사' , 9 );
	
SELECT * FROM book_category;
	


CREATE TABLE BOOK_INFO(
	BOOK_INFO_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	BOOK_INFO_ATTACHED_FILE_NAME VARCHAR(100) ,		
	BOOK_INFO_ORIGIN_FILE_NAME VARCHAR(100) , 			
	BOOK_BORROW_AVAILABLE VARCHAR(3) DEFAULT 'Y', 	
	BOOK_BORROW_MEMBER_NUM INT , 				
	BOOK_BORROW_DATE DATETIME , 					
	BOOK_BORROW_CNT INT , 								
	BOOK_INTRO VARCHAR(100) , 								
	BOOK_CATE_CODE INT REFERENCES BOOK_CATEGORY(BOOK_CATE_CODE) ,
	BOOK_CODE VARCHAR(15) REFERENCES BOOK(BOOK_CODE)); 	



SELECT * FROM book;
-- DROP TABLE book_info;


-- 문자열로 바꾸고 숫자부분만 훔치고 최대값을 구한다음에 +1 해주기
SELECT MAX(CAST(SUBSTRING((BOOK_CODE),3) AS INT)+1) FROM book;


-- 위에서 결과물에 GR 붙이고 0으로 채워주기
SELECT CONCAT('GR',LPAD( (SELECT MAX(CAST(SUBSTRING((BOOK_CODE),3) AS INT)+1) FROM book) , 10 ,'0'));
-- 책 정보

-- 이건 외부에 있는 데이터 불러오는기능
LOAD DATA LOCAL INFILE 'C:/bookdata/insertData_2.csv'
INTO TABLE BOOK
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT * FROM users;

SELECT 
user_id
,user_pw
FROM users
WHERE user_id = 'admin' AND user_pw = '0';

select
	USER_ID
   ,USER_PW
FROM USERS
WHERE USER_ID = 'admin'
AND USER_PW = '0';



-- 2024-02-28 
CREATE TABLE BOARD(
  BOARD_NO INT AUTO_INCREMENT PRIMARY KEY
  , BOARD_TITLE VARCHAR(50) NOT NULL
  , CONTENT VARCHAR(200)
  , BOARD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
  , USER_CODE INT REFERENCES USERS(USER_CODE) 
  , BOARD_CNT INT
  , BOARD_TYPE INT NOT NULL
);

CREATE TABLE EVENT_TABLE(
	EVENT_CODE INT AUTO_INCREMENT PRIMARY KEY
	, EVENT_NAME VARCHAR(50) NOT NULL 
	, TEACHER VARCHAR(20) NOT NULL 
	, TARGET VARCHAR(50) NOT NULL
	, EVENT_OPEN DATETIME NOT NULL
	, EVENT_CLOSE DATETIME NOT NULL
	, TO_DATE DATE NOT NULL
	, FROM_DATE DATE NOT NULL
	, PERSONNEL INT DEFAULT 0
	, MAXIMUM_PERSON INT
	, EVENT_STATUS VARCHAR(10) NOT NULL
	, STATUS_INDEX INT NOT NULL
);

DROP FROM cul_board;

UPDATE cul_board
SET USER_CODE = 10,
	BOARD_CNT = 0
WHERE 
	board_NO = 1;


DROP TABLE image;

CREATE TABLE ATTACHED_FILE (
   ATTACHED_CODE INT AUTO_INCREMENT PRIMARY KEY
   , ORIGIN_FILE_NAME VARCHAR(100) NOT NULL
   , ATTACHED_FILE_NAME VARCHAR(100) NOT NULL
   , IS_MAIN VARCHAR(2) -- ' MAIN 일때 Y , 아닐때 N
   , BOARD_NO INT NOT NULL REFERENCES BOARD (BOARD_NO)
);



DROP TABLE IMAGE;

SELECT * FROM USERS;

SELECT
BOARD.BOARD_NO
, BOARD_TITLE
, CONTENT
, BOARD_DATE
, BOARD.USER_CODE
, BOARD_CNT
, USER_ID
, IMG_CODE
, ORIGIN_FILE_NAME
, ATTACHED_FILE_NAME
, IS_MAIN
FROM board INNER JOIN USERS
ON BOARD.USER_CODE = USERS.USER_CODE
INNER JOIN IMAGE
ON BOARD.BOARD_NO = IMAGE.BOARD_NO;

UPDATE USERS
SET IS_ADMIN = 'Y'
WHERE USER_CODE = 13;


SELECT
   BOARD.BOARD_NO
   , BOARD_TITLE
   , CONTENT
   , DATE_FORMAT(BOARD_DATE, '%Y-%m-%d') AS BOARD_DATE
   , BOARD.USER_CODE
   , BOARD_CNT
   
FROM board;