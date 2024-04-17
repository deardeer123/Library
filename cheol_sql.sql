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

SELECT * FROM board;
-- 헤더 파일 데이터 도서 관리쪽임
-- 2024-03-18 메뉴명 변경
INSERT INTO HEADER_MENU(
	MENU_NAME , MENU_PAGE , MENU_INDEX , MENU_TYPE)
	VALUES
	('대출 반납', 'borrowReturn', 1 , 'library'),
	('이용자', 'user', 2 , 'library') ,
	('자료 등록', 'bookReg', 3 , 'library') ,
	('자료 변경/열람', 'bookChangeAndOpen', 4 , 'library') ,
	('통계', 'statistics', 5 , 'library');
	
SELECT * FROM header_menu;

UPDATE header_menu
SET
	menu_name = '자료 등록' ,
	menu_page = 'bookReg'
WHERE
	MENU_NUM=3;
	

UPDATE header_menu
SET
	MENU_NAME = '자료 변경/열람' ,
	MENU_PAGE = 'bookChangeAndOpen'
WHERE
	MENU_NUM=4;
	
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

SELECT *
FROM side_menu
WHERE side_menu_page = 'notice';

SELECT *
FROM header_menu
WHERE menu_num = 9;

SELECT 
side_menu.SIDE_MENU_INDEX ,
header_menu.MENU_INDEX ,
side_menu.SIDE_MENU_NUM
FROM
side_menu INNER JOIN header_menu
ON side_menu.MENU_NUM = header_menu.MENU_NUM
WHERE side_menu.side_menu_page = 'notice';

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
SELECT * FROM side_menu;
	
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
	
	 DROP TABLE book_borrow;
	 DROP TABLE book_return;
	DROP table users;
	
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
	header_menu.MENU_TYPE = 'web' AND header_menu.MENU_NUM = 10;
	
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
	
CREATE TABLE BOOK_MID_CATEGORY(
	BOOK_MID_CATE_CODE INT AUTO_INCREMENT PRIMARY KEY,
	BOOK_MID_CATE_NAME VARCHAR(35) NOT NULL,
	BOOK_MID_CATE_INDEX INT NOT NULL,
	BOOK_CATE_CODE INT NOT NULL REFERENCES book_category(BOOK_CATE_CODE));

-- DROP TABLE BOOK_MID_CATEGORY;

SELECT * FROM book_mid_category;

UPDATE book_mid_category
SET BOOK_MID_CATE_NAME = '회화,도화'
WHERE BOOK_MID_CATE_CODE = 59;

SELECT * FROM users;

DROP TABLE book_borrow;
DROP table book_return;

CREATE TABLE BOOK_BNR(
	BORROW_CODE INT PRIMARY KEY AUTO_INCREMENT
	, BORROW_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
	, RETURN_YN VARCHAR(2) DEFAULT 'N'
	, EX_RETURN_DATE DATETIME
	, RETURN_DATE DATETIME
	, USER_CODE INT REFERENCES users(USER_CODE)
	, BOOK_CODE VARCHAR(20) REFERENCES BOOK(BOOK_CODE)
);

SELECT * FROM book_bnr;

INSERT INTO BOOK_MID_CATEGORY(
	BOOK_MID_CATE_NAME ,
	BOOK_MID_CATE_INDEX ,
	BOOK_CATE_CODE)
	VALUES
	-- 총류
	('도서학,서지학' , 1 , 1),
	('문헌정보학' , 2 , 1 ),
	('백과사전' , 3 , 1) ,
	('강연집,수필집,연설문집' , 4 , 1) ,	
	('일반연속간행물' , 5 , 1) ,	
	('일반학회,단체,협회,기관' , 6 , 1) ,	
	('신문,언론,저널리즘' , 7 , 1) ,	
	('일반전집,총서' , 8 , 1) ,	
	('향토자료' , 9 , 1) ,
	-- 철학
	('형이상학' , 1 , 2) ,
	('인신론,인과론,인간학' , 2 , 2) ,
	('철학의체계' , 3 , 2) ,
	('경학' , 4 , 2) ,
	('동양철학,사상' , 5 , 2) ,
	('서양철학' , 6 , 2) ,
	('논리학' , 7 , 2) ,
	('심리학' , 8 , 2) ,
	('윤리학,도덕철학' , 9 , 2) ,
	-- 종교
	('비교종교' , 1 , 3) ,
	('불교' , 2 , 3) ,
	('기독교' , 3 , 3) ,
	('도교' , 4 , 3) ,
	('천도교' , 5 , 3) ,
	('신도' , 6 , 3) ,
	('힌두교,브라만교' , 7 , 3) ,
	('이슬람교(회교)' , 8 , 3) ,
	('기타 제종교' , 9 , 3) ,
	-- 사회과학
	('통계학' , 1 , 4) ,
	('경제학' , 2 , 4) ,
	('사회학,사회문제' , 3 , 4) ,
	('정치학' , 4 , 4) ,
	('행정학' , 5 , 4) ,
	('법학' , 6 , 4) ,
	('교육학' , 7 , 4) ,
	('풍속,예절,민속학' , 8 , 4) ,
	('국방,군사학' , 9 , 4) ,
	-- 자연과학
	('수학' , 1 , 5) ,
	('물리학' , 2 , 5) ,
	('화학' , 3 , 5) ,
	('천문학' , 4 , 5) ,
	('지학' , 5 , 5) ,
	('광물학' , 6 , 5) ,
	('생명과학' , 7 , 5) ,
	('식물학' , 8 , 5) ,
	('동물학' , 9 , 5) ,
	-- 기술과학
	('의학' , 1 , 6) ,
	('농업,농학' , 2 , 6) ,
	('공학,공업일반,토목공학,환경공학' , 3 , 6) ,
	('건축공학' , 4 , 6) ,
	('기계공학' , 5 , 6) ,
	('전기공학,전자공학' , 6 , 6) ,
	('화학공학' , 7 , 6) ,
	('제조업' , 8 , 6) ,
	('생활과학' , 9 , 6) ,
	-- 예술
	('건축물' , 1 , 7) ,
	('조각,조형,예술' , 2 , 7) ,
	('공예,장식미술' , 3 , 7) ,
	('서예' , 4 , 7) ,
	('회화,도화' , 5 , 7) ,
	('사진예술' , 6 , 7) ,
	('음악' , 7 , 7) ,
	('공연예술,매체예술' , 8 , 7) ,
	('오락,스포츠' , 9 , 7) ,
	-- 언어
	('한국어' , 1 , 8) ,
	('중국어' , 2 , 8) ,
	('일본어,기타아시아제어' , 3 , 8) ,
	('영어' , 4 , 8) ,
	('독일어' , 5 , 8) ,
	('프랑스어' , 6 , 8) ,
	('스페인어,포르투칼어' , 7 , 8) ,
	('이탈리라어' , 8 , 8) ,
	('기타제어' , 9 , 8) ,
	-- 문학
	('한국문학' , 1 , 9) ,
	('중국문학' , 2 , 9) ,
	('일본문학,기타아시아문학' , 3 , 9) ,
	('영미문학' , 4 , 9) ,
	('독일문학' , 5 , 9) ,
	('프랑스문학' , 6 , 9) ,
	('스페인,포르투칼문학' , 7 , 9) ,
	('이탈리아문학' , 8 , 9) ,
	('기타제문학' , 9 , 9) ,
	-- 역사
	('아시아' , 1 , 10) ,
	('유럽' , 2 , 10) ,
	('아프리카' , 3 , 10) ,
	('북아메리카' , 4 , 10) ,
	('남아메리카' , 5 , 10) ,
	('오세아니아' , 6 , 10) ,
	('양극지방' , 7 , 10) ,
	('지리' , 8 , 10) ,
	('전기' , 9 , 10);

SELECT 
	book_category.BOOK_CATE_CODE , 
	BOOK_CATE_NAME ,
	BOOK_CATE_INDEX ,
	BOOK_MID_CATE_CODE ,
	BOOK_MID_CATE_NAME ,
	BOOK_MID_CATE_INDEX
FROM
	book_category INNER JOIN BOOK_MID_CATEGORY
	ON book_category.BOOK_CATE_CODE = BOOK_MID_CATEGORY.BOOK_CATE_CODE
WHERE
	book_category.BOOK_CATE_CODE = 1;




CREATE TABLE BOOK(
   BOOK_CODE VARCHAR(15) PRIMARY KEY,
   BOOK_TITLE VARCHAR(100) NOT NULL,
   BOOK_WRITER VARCHAR(100) NOT NULL,
   BOOK_PUB VARCHAR(30) NOT NULL,
   BOOK_YEAR VARCHAR(20) NOT NULL);
   
   SELECT * FROM book;

SELECT 
	book_code ,
	book_title
FROM
 	book
WHERE
	book_code IN ('GR0000000001' , 'GR0000000002', 'GR0000000003');

CREATE TABLE BOOK_INFO(
	BOOK_INFO_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	BOOK_INFO_ATTACHED_FILE_NAME VARCHAR(100) ,		
	BOOK_INFO_ORIGIN_FILE_NAME VARCHAR(100) , 			
	BOOK_BORROW_AVAILABLE VARCHAR(3) DEFAULT 'Y', 					
	BOOK_BORROW_CNT INT DEFAULT 0, 								
	BOOK_INTRO VARCHAR(2000) , -- 2024-02-29 변경함
	BOOK_REGDATE DATETIME DEFAULT CURRENT_TIMESTAMP , -- 2024-02-29 변경함  								
	BOOK_CATE_CODE INT REFERENCES BOOK_CATEGORY(BOOK_CATE_CODE) ,
	BOOK_MID_CATE_CODE INT REFERENCES BOOK_MID_CATEGORY(BOOK_MID_CATE_CODE) ,
	BOOK_CODE VARCHAR(15) REFERENCES BOOK(BOOK_CODE)); 

-- 책소개 최대길이가 조금 모자랄거 같아서 늘렸어요 만약에 book_info 테이블을 만들었다면 아래 쿼리문 실행해주세요.
ALTER TABLE book_info modify book_intro VARCHAR(2000);
ALTER TABLE book_info ADD BOOK_REGDATE DATETIME DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE book_info ADD BOOK_MID_CATE_CODE INT REFERENCES BOOK_MID_CATEGORY(BOOK_MID_CATE_CODE); -- 2024-03-04 중분류 추가

UPDATE book_info
SET BOOK_MID_CATE_CODE = 1;

SELECT * FROM book_info;

-- DELETE FROM book_info;

CREATE TABLE BOOK_BREAKAGE(
   BOOK_CODE VARCHAR(15) PRIMARY KEY,
   BOOK_TITLE VARCHAR(100) NOT NULL,
   BOOK_WRITER VARCHAR(100) NOT NULL,
   BOOK_PUB VARCHAR(30) NOT NULL,
   BOOK_YEAR VARCHAR(20) NOT NULL);

-- DROP TABLE book_breakage;

-- 작살난 책 상세 테이블
CREATE TABLE BOOK_BREAKAGE_INFO(
	BOOK_INFO_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	BOOK_INFO_ATTACHED_FILE_NAME VARCHAR(100) ,		
	BOOK_INFO_ORIGIN_FILE_NAME VARCHAR(100) , 			
	BOOK_BORROW_AVAILABLE VARCHAR(3) DEFAULT 'Y', 					
	BOOK_BORROW_CNT INT DEFAULT 0, 								
	BOOK_INTRO VARCHAR(2000) , -- 2024-02-29 변경함
	BOOK_REGDATE DATETIME DEFAULT CURRENT_TIMESTAMP , -- 2024-02-29 변경함  								
	BOOK_CATE_CODE INT REFERENCES BOOK_CATEGORY(BOOK_CATE_CODE) ,
	BOOK_MID_CATE_CODE INT REFERENCES BOOK_MID_CATEGORY(BOOK_MID_CATE_CODE) ,
	BOOK_CODE VARCHAR(15) REFERENCES BOOK_BREAKAGE(BOOK_CODE)); 

-- DROP TABLE book_breakage;
-- DROP TABLE BOOK_BREAKAGE_INFO;



INSERT INTO book_info(
	BOOK_INFO_ATTACHED_FILE_NAME ,
	BOOK_INFO_ORIGIN_FILE_NAME ,
	BOOK_INTRO ,
	BOOK_CATE_CODE ,
	BOOK_CODE)
	VALUES
	(
	'book_character_smile.png',
	'book_character_smile.png' ,
	'',
	1,
	'GR0000000001'
	);

        	

UPDATE book_info
        SET
            BOOK_INFO_ATTACHED_FILE_NAME = 'book_character_smile.png' ,
            BOOK_INFO_ORIGIN_FILE_NAME = 'book_character_smile.png' ,
            BOOK_INTRO = '디폴트입니다.' ,
            BOOK_CATE_CODE = 1
        WHERE
            BOOK_CODE in (SELECT book_code FROM book);
            
SELECT book_code FROM book;
SELECT * FROM book_info;


-- 뺀거임
-- BOOK_BORROW_MEMBER_NUM INT DEFAULT 0, 				
--	BOOK_BORROW_DATE DATETIME DEFAULT 0,

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
	




SELECT * FROM book;
-- DROP TABLE book_info;
-- DROP VIEW BOOK_DETAIL_VIEW;

-- 대출 반납 테이블 (24.03.12 생성)
CREATE TABLE BOOK_BNR(
	BORROW_CODE INT PRIMARY KEY AUTO_INCREMENT
	, BORROW_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
	, RETURN_YN VARCHAR(2) DEFAULT 'N'
	, EX_RETURN_DATE DATETIME
	, RETURN_DATE DATETIME
	, USER_CODE INT REFERENCES users(USER_CODE)
	, BOOK_CODE VARCHAR(20) REFERENCES BOOK(BOOK_CODE)
);

SELECT * FROM book_bnr;
SELECT * FROM book;
SELECT * FROM book_info;
SELECT * FROM users;

SELECT * FROM book_breakage;
SELECT * FROM book_breakage_info;

-- 책  상세 검색하기

		SELECT
         BOOK.BOOK_CODE ,
         BOOK.BOOK_TITLE ,
         BOOK.BOOK_WRITER ,
         BOOK.BOOK_PUB ,
         BOOK.BOOK_YEAR ,
         book_info.BOOK_BORROW_AVAILABLE ,
         book_info.BOOK_BORROW_CNT ,
         book_info.BOOK_INFO_ORIGIN_FILE_NAME ,
         book_info.BOOK_INFO_ATTACHED_FILE_NAME ,
         book_info.BOOK_INTRO ,
         book_info.BOOK_CATE_CODE ,
      	book_category.BOOK_CATE_NAME ,
         book_info.BOOK_MID_CATE_CODE ,
         book_mid_category.BOOK_MID_CATE_NAME ,
         book_bnr.BORROW_CODE ,
         book_bnr.BORROW_DATE ,
         book_bnr.RETURN_YN,
         book_bnr.EX_RETURN_DATE ,
         booK_bnr.RETURN_DATE,
         book_bnr.USER_CODE,
         users.USER_NAME,
         users.USER_ID
      FROM
         book LEFT OUTER join book_info
        	ON BOOK.BOOK_CODE = book_info.BOOK_CODE
        	LEFT OUTER JOIN book_category
        	ON book_info.BOOK_CATE_CODE = book_category.BOOK_CATE_CODE
        	LEFT OUTER JOIN book_mid_category
        	ON book_info.BOOK_MID_CATE_CODE = book_mid_category.BOOK_MID_CATE_CODE
        	LEFT OUTER JOIN booK_bnr
			ON BOOK.BOOK_CODE = booK_bnr.BOOK_CODE
			LEFT OUTER JOIN users
			ON book_bnr.USER_CODE = users.USER_CODE
		where
			BOOK.book_code = 'GR0000000001'
		ORDER BY book.BOOK_CODE;

SELECT * FROM BOOK_DETAIL_VIEW WHERE book_code = 'GR0000000001';

-- 책 검색 뷰
CREATE VIEW find_book_view as
SELECT
   book.BOOK_CODE ,
   book.BOOK_TITLE ,
   book.BOOK_WRITER ,
   book.BOOK_PUB ,
   book.BOOK_YEAR ,
   book_info.BOOK_BORROW_AVAILABLE,
   book_info.book_intro,
   book_info.BOOK_INFO_ATTACHED_FILE_NAME ,
   book_category.BOOK_CATE_NAME ,
   book_mid_category.BOOK_MID_CATE_NAME,
   book_info.BOOK_REGDATE
FROM
   book INNER JOIN book_info
   ON book.BOOK_CODE = book_info.BOOK_CODE
   INNER JOIN book_category
   ON book_info.BOOK_CATE_CODE = book_category.BOOK_CATE_CODE
   INNER JOIN book_mid_category
   ON book_info.BOOK_MID_CATE_CODE = book_mid_category.BOOK_MID_CATE_CODE
WHERE
   1 = 1 
ORDER BY book_code;

SELECT * FROM find_book_view
WHERE book_code = 'GR0000000001'
LIMIT 20 OFFSET 0;

SELECT
	book_cate_name ,
	book_mid_cate_name
FROM book_info INNER JOIN book_category
ON book_info.BOOK_CATE_CODE = book_category.BOOK_CATE_CODE
INNER JOIN book_mid_category
ON book_info.BOOK_MID_CATE_CODE = book_mid_category.BOOK_MID_CATE_CODE
WHERE book_code = 'GR0000000001';




-- 문자열로 바꾸고 숫자부분만 훔치고 최대값을 구한다음에 +1 해주기
SELECT MAX(CAST(SUBSTRING((BOOK_CODE),3) AS INT)+1) FROM book;


-- 위에서 결과물에 GR 붙이고 0으로 채워주기
SELECT CONCAT('GR',LPAD( (SELECT MAX(CAST(SUBSTRING((BOOK_CODE),3) AS INT)+1) FROM book) , 10 ,'0'));


SELECT * 
FROM book
ORDER BY book_code
LIMIT 10 OFFSET 30;

SELECT *
FROM book_info;

-- 캘린더
CREATE TABLE calendar(
	CALENDAR_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	CALENDAR_TITLE VARCHAR(50) NOT NULL ,
	CALENDAR_START DATETIME NOT NULL
	);
	
-- 추가
ALTER TABLE calendar ADD CALENDAR_URL VARCHAR(100);
ALTER TABLE calendar ADD CALENDAR_TYPE VARCHAR(20);
-- 변경
ALTER TABLE calendar DROP CALENDAR_TYPE;
ALTER TABLE calendar ADD CALENDAR_COLOR VARCHAR(20);

SELECT * FROM USERs;

SELECT * FROM calendar;

SELECT
        CALENDAR_TITLE ,
        CALENDAR_START ,
        CASE
            WHEN CALENDAR_TYPE = 'movie' THEN 'blue'
            WHEN CALENDAR_TYPE = 'event' THEN 'blue'
            WHEN CALENDAR_TYPE = 'closeDay' Then 'red'
            ELSE 'green'
        END ,
        CALENDAR_URL
        FROM
        calendar;


-- 캘린더 데이터
INSERT INTO calendar(
	CALENDAR_TITLE ,
	CALENDAR_START )
	VALUES
	('테스트1' , '2024-03-06') ,
	('테스트2' , '2024-03-07') ,
	('테스트3' , '2024-03-08') ;
	

-- 책 정보

-- 이건 외부에 있는 데이터 불러오는기능
LOAD DATA LOCAL INFILE 'C:/bookdata/insertData_2.csv'
INTO TABLE BOOK
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT ITEM_CODE
	, ITEM_NAME
	, ITEM_STOCK
	, ITEM_STATUS
	, IF(ITEM_STATUS = 1 , '준비중' , IF(ITEM_STATUS = 2, '판매중', '매진')) AS '상태1'
	, 
	CASE
		WHEN ITEM_STATUS = 1 THEN '준비 중'
		WHEN ITEM_STATUS = 2 THEN '판매 중'
		ELSE '매진'
	END AS STR_STATUS
FROM
	shop_item;
	
SELECT
        book_breakage.BOOK_CODE,
        book_breakage.BOOK_TITLE,
        book_breakage.BOOK_WRITER,
        book_breakage.BOOK_PUB,
        book_breakage.BOOK_YEAR,
        book_breakage_info.BOOK_INFO_ORIGIN_FILE_NAME,
        book_breakage_info.BOOK_INFO_ATTACHED_FILE_NAME,
        book_breakage_info.BOOK_INTRO,
        book_breakage_info.BOOK_CATE_CODE,
        book_breakage_info.BOOK_MID_CATE_CODE
        FROM
        book_breakage LEFT OUTER JOIN book_breakage_info
        ON
        book_breakage.BOOK_CODE = book_breakage_info.BOOK_CODE;
        
        
        
 SELECT
            COUNT(BOOK_CODE)
        FROM
        book_breakage;
        
CREATE TABLE BOARD(
  BOARD_NUM INT AUTO_INCREMENT PRIMARY KEY
  , BOARD_TITLE VARCHAR(50) NOT NULL
  , CONTENT VARCHAR(200)
  , BOARD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
  , USER_CODE INT REFERENCES USERS(USER_CODE) 
  , BOARD_CNT INT
  , BOARD_TYPE INT NOT NULL
);


CREATE TABLE ATTACHED_FILE (
   ATTACHED_CODE INT AUTO_INCREMENT PRIMARY KEY
   , ORIGIN_FILE_NAME VARCHAR(100) NOT NULL
   , ATTACHED_FILE_NAME VARCHAR(100) NOT NULL
   , IS_MAIN VARCHAR(2) -- ' MAIN 일때 Y , 아닐때 N
   , BOARD_NUM INT NOT NULL REFERENCES BOARD (BOARD_NUM) ON DELETE CASCADE
);

SELECT * FROM attached_file;

SELECT * FROM board;

SELECT * FROM users;

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
	2
	, 'aaaa'
	, '1111'
	, '관리자'
	, '010-1111-2345'
	, 12345
	, '울산시 남구'
	, '그린아카데미'
	, '남자'
	, '2345@gmail.com'
	, 'N'
);

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
	3
	, 'bbbb'
	, '2222'
	, '관리자'
	, '010-1111-3333'
	, 12345
	, '울산시 남구'
	, '그린아카데미'
	, '남자'
	, '2345@gmail.com'
	, 'Y'
);
        
CREATE TABLE ASK_AND_ANSWER_BOARD(
	ASK_AND_ANSWER_BOARD_NUM INT AUTO_INCREMENT PRIMARY KEY
	, ASK_AND_ANSWER_BOARD_PASSWORD VARCHAR(50)
	, ASK_AND_ANSWER_BOARD_PUBLIC_OR_PRIVATE VARCHAR(10)
	, IS_ANSWER_BOARD VARCHAR(10) DEFAULT 'N' 
	, IF_ANSWER_BOARD_NUM INT NOT NULL DEFAULT 0
	, CHK_ASK_USER_CODE INT DEFAULT 0
	, ORIGIN_ORDER_NUM INT
	, ANSWER_ORDER_NUM INT
	, BOARD_NUM INT NOT NULL REFERENCES board(BOARD_NUM) ON DELETE CASCADE
	);

SELECT * FROM board;
SELECT * FROM attached_file;
SELECT * FROM ASK_AND_ANSWER_BOARD;
SELECT * FROM users;

DELETE FROM board;
DELETE FROM attached_file;
DELETE FROM ask_and_answer_board;



SELECT 
	BOARD.BOARD_NUM ,
	BOARD.BOARD_TITLE ,
	DATE_FORMAT(BOARD.BOARD_DATE, '%Y-%m-%d') ,
	USERS.USER_NAME ,
	BOARD.BOARD_CNT
FROM
	BOARD INNER JOIN users
	ON board.USER_CODE = users.USER_CODE
WHERE
	board_type = 30;
	
	
	
SELECT
        BOARD.BOARD_NUM ,
        BOARD.BOARD_TITLE ,
        DATE_FORMAT(BOARD.BOARD_DATE, '%Y-%m-%d') AS BOARD_DATE ,
        USERS.USER_NAME ,
        BOARD.BOARD_CNT ,
        ASK_AND_ANSWER_BOARD.ASK_AND_ANSWER_BOARD_NUM ,
        ASK_AND_ANSWER_BOARD.ASK_AND_ANSWER_BOARD_PUBLIC_OR_PRIVATE ,
        ASK_AND_ANSWER_BOARD.IS_ANSWER_BOARD ,
        ASK_AND_ANSWER_BOARD.IF_ANSWER_BOARD_NUM
        FROM
        BOARD INNER JOIN users
        ON board.USER_CODE = users.USER_CODE
        INNER JOIN ASK_AND_ANSWER_BOARD
        ON board.BOARD_NUM = ASK_AND_ANSWER_BOARD.BOARD_NUM
        WHERE
        board_type = 30 AND ask_and_answer_board.IS_ANSWER_BOARD = 'N'
      
        ORDER BY ask_and_answer_board.ASK_AND_ANSWER_BOARD_NUM desc;
    
	
	SELECT
        BOARD.BOARD_NUM ,
        BOARD.BOARD_TITLE ,
        DATE_FORMAT(BOARD.BOARD_DATE, '%Y-%m-%d') AS BOARD_DATE ,
        USERS.USER_NAME ,
        BOARD.BOARD_CNT ,
        ASK_AND_ANSWER_BOARD.ASK_AND_ANSWER_BOARD_NUM ,
        ASK_AND_ANSWER_BOARD.ASK_AND_ANSWER_BOARD_PUBLIC_OR_PRIVATE ,
        ASK_AND_ANSWER_BOARD.IS_ANSWER_BOARD ,
        ASK_AND_ANSWER_BOARD.IF_ANSWER_BOARD_NUM,
        ask_and_answer_board.ORIGIN_ORDER_NUM ,
        ask_and_answer_board.ANSWER_ORDER_NUM
        FROM
        BOARD INNER JOIN users
        ON board.USER_CODE = users.USER_CODE
        INNER JOIN ASK_AND_ANSWER_BOARD
        ON board.BOARD_NUM = ASK_AND_ANSWER_BOARD.BOARD_NUM
        WHERE
        board_type = 30
      
        ORDER BY ask_and_answer_board.ORIGIN_ORDER_NUM DESC , ask_and_answer_board.ANSWER_ORDER_NUM;
        
        
	
	SELECT * from board;
	SELECT * FROM ask_and_answer_board;
	
	SELECT * FROM ask_and_answer_board;
	
	ALTER TABLE ask_and_answer_board ADD column ORIGIN_ORDER_NUM INT;
	ALTER TABLE ask_and_answer_board ADD COLUMN ANSWER_ORDER_NUM INT;
	
	SELECT * FROM ask_and_answer_board;
	
SELECT * FROM users;

	SELECT 
		CASE
			when MAX(origin_order_num) IS null then 1
			ELSE MAX(origin_order_num)
		END
	FROM
		ask_and_answer_board;
		
		
	select
		case
			when MAX(ANSWER_ORDER_NUM) IS NULL then 1
			ELSE MAX(ANSWER_ORDER_NUM)
		end
	from
		ask_and_answer_board;
		
	SELECT MAX(origin_order_num)
	FROM ask_and_answer_board;
	
	SELECT 
		USER_CODE ,
		USER_ID ,
		USER_NAME,
		USER_TEL
	FROM
		users
	where
		user_code = 2;
	
	
	SELECT * 
	FROM board;
	
	

SELECT
        header_menu.MENU_NUM ,
        MENU_NAME ,
        MENU_INDEX ,
        MENU_TYPE,
        SIDE_MENU_NAME ,
        SIDE_MENU_PAGE ,
        SIDE_MENU_INDEX ,
        SIDE_MENU.SIDE_MENU_NUM
        FROM
        header_menu INNER JOIN side_menu
        ON header_menu.MENU_NUM = side_menu.MENU_NUM
        WHERE header_menu.MENU_TYPE = 'library';
        

SELECT * FROM year_by_time;

SELECT * FROM calendar;

CREATE TABLE board_plus(
   PLUS_CODE INT PRIMARY KEY AUTO_INCREMENT
   ,TEACHER VARCHAR(20) NOT NULL
   ,TARGET VARCHAR(50) NOT NULL
   ,OPEN_DATE DATETIME NOT NULL
   ,CLOSE_DATE DATETIME NOT NULL
   ,TO_DATE DATE NOT NULL
   ,FROM_DATE DATE NOT NULL
   ,PERSONNEL INT DEFAULT 0
   ,MAXIMUM_PERSON INT
   ,EVENT_STATUS VARCHAR(20)
   ,BOARD_NUM INT REFERENCES board (BOARD_NUM)
);
-- DROP TABLE YEAR_BY_TIME;

CREATE TABLE Electric_Accident_Burn_Range (
YEARS INT PRIMARY KEY ,
BURNRANGE0_5 INT ,
BURNRANGE6_10 INT ,
BURNRANGE11_21 INT ,
BURNRANGE21_30 INT ,
BURNRANGE31_40 INT ,
BURNRANGE41_50 INT ,
BURNRANGE51_60 INT ,
BURNRANGE60_OVER INT
);

SELECT * FROM Electric_Accident_Burn_Range;

CREATE TABLE ELECTRIC_ACCIDENTS_BY_DAY (
OCCURRED_YEAR INT PRIMARY KEY , 
SUNDAY INT ,
MONDAY INT ,
TUESDAY INT ,
WEDNESDAY INT ,
THURSDAY INT , 
FRIDAY INT , 
SATURDAY INT 
)

SELECT * FROM electric_accidents_burn_range;
DELETE FROM electric_accidents_burn_range;


INSERT INTO electric_accidents_burn_range
 VALUES 
 (2016,300,184,35,16,3,2,2,4),
 (2017,357,102,38,24,7,2,1,1),
 (2018,339,101,46,19,4,4,1,1),
 (2019,393,61,36,8,6,2,0,2),
 (2020,272,67,35,19,8,2,2,3),
 (2021,261,74,45,19,8,3,2,0),
 (2022,258,77,38,14,11,3,1,3);
DROP TABLE electric_accidents_burn_range;
DROP TABLE electric_accidents_by_time ;
DROP TABLE electric_accidents_by_day;
DROP TABLE current_status_of_electric_accidents;
DROP TABLE factors_causing_electric_accidents;
DROP TABLE electric_accidents_fire_statistics;

SELECT * FROM electric_accidents_burn_range;
SELECT * FROM electric_accidents_by_time;
SELECT * FROM electric_accidents_by_day;
SELECT * FROM current_status_of_electric_accidents;
SELECT * FROM factors_causing_electric_accidents;
SELECT * FROM electric_accidents_fire_statistics;

CREATE TABLE asdf{
 asdf11 FLOAT 

 INSERT INTO ELECTRIC_ACCIDENTS_BY_TIME VALUES
  (2016,5,1,1,11,39,90,58,69,75,42,33,13,109),
  (2017,3,5,3,18,58,95,61,101,75,43,32,19,19),
  (2018,5,6,7,17,57,104,64,90,71,36,25,16,17),
  (2019,3,6,1,5,38,106,77,118,65,51,26,12,0),
  (2020,6,2,4,24,85,72,64,64,33,18,16,20,0),
  (2021,4,2,5,21,89,57,89,70,33,22,12,8,0),
  (2022,1,5,5,27,71,71,74,73,34,20,8,16,0);
  
  
  SELECT
                     
   sum(SUNDAY) AS SUNDAY ,
   sum(MONDAY) ,
   sum(TUESDAY) ,
   sum(WEDNESDAY) ,
	sum(THURSDAY) ,
   sum(FRIDAY) ,
   sum(SATURDAY)
FROM
   electric_accidents_by_day;

SELECT * from electric_accidents_by_day;

SELECT
                        max(SUNDAY) as sunday ,
                        max(MONDAY) as MONDAY ,
                        max(TUESDAY) as TUESDAY,
                        max(WEDNESDAY) as WEDNESDAY ,
                        max(THURSDAY) as THURSDAY ,
                        max(FRIDAY) as FRIDAY,
                        max(SATURDAY) as SATURDAY
                FROM
                        electric_accidents_by_day;
            	
SELECT * FROM find_book_view;

SELECT * FROM book_info;
  	
  SELECT
        find_book_view.BOOK_CODE,
        BOOK_TITLE,
        BOOK_WRITER,
        BOOK_PUB,
        BOOK_YEAR,
        book_info.BOOK_BORROW_AVAILABLE,
        book_info.book_intro,
        book_info.BOOK_INFO_ATTACHED_FILE_NAME,
        BOOK_CATE_NAME,
        BOOK_MID_CATE_NAME,
        book_info.BOOK_REGDATE
        FROM find_book_view INNER JOIN book_info
        ON find_book_view.BOOK_CODE = book_info.BOOK_CODE
        ORDER by book_info.BOOK_BORROW_CNT desc
        LIMIT 37; 	
  	
SELECT
 book_title,
 book_writer,
 BOOK_BORROW_CNT
 FROM book INNER JOIN book_info
 ON book.BOOK_CODE = book_info.BOOK_CODE
 ORDER by book_info.BOOK_BORROW_CNT desc
LIMIT 36;
 
 
SELECT *
FROM book INNER JOIN book_info
ON book.BOOK_CODE = book_info.BOOK_CODE;