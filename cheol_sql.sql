CREATE TABLE HEADER_MENU(
	MENU_NUM INT AUTO_INCREMENT PRIMARY KEY ,
	MENU_NAME VARCHAR(20) NOT NULL ,
	MENU_PAGE VARCHAR(50) NOT NULL ,
	MENU_INDEX INT NOT NULL);
	
-- DROP TABLE header_menu;

CREATE TABLE SIDE_MENU(
	SIDE_MENU_NUM INT AUTO_INCREMENT PRIMARY KEY,
	SIDE_MENU_NAME VARCHAR(20) NOT NULL ,
	SIDE_MENU_PAGE VARCHAR(50) NOT NULL ,
	SIDE_MENU_INDEX INT NOT NULL ,
	MENU_NUM INT REFERENCES HEADER_MENU(menu_num));

-- DROP TABLE side_menu;

-- 헤더 파일 데이터
INSERT INTO HEADER_MENU(
	MENU_NAME , MENU_PAGE , MENU_INDEX)
	VALUES
	('대출 반납', 'borrowReturn', 1),
	('이용자', 'user', 2) ,
	('구입', 'buy', 3) ,
	('등록 열람', 'regAndView', 4) ,
	('통계', 'statistics', 5);

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
	('대출 반납', 'borrowReturn', 1 , 1),
	('일관 반납', 'consistentReturn', 2 , 1),
	('대출 반납 관리', 'borrowReturnManagement', 3 , 1),
	('예약정보 관리', 'reservationInfo', 4 , 1),
	('출력이력 관리', 'outputHistory', 5 , 1) ,
	-- 이용자
	('이용자 관리', 'user' , 1 , 2) ,
	('이용자 승인', 'userApproval', 2 , 2) ,
	('연체자 관리', 'delinquent', 3 , 2) ,
	-- 구입
	('희망 자료' , 'wishBook' , 1 , 3) ,
	('삭제 자료' , 'deleteBook', 2 , 3) ,
	('구입 자료' , 'buyBook', 3 , 3) ,
	('기증 자료' , 'donatedBook', 4 , 3) ,
	-- 등록 열람
	('작업 자료 관리', 'workingBook', 1, 4),
	('소장 자료 관리', 'collectionBook', 2, 4),
	('마크 반입', 'markImport', 3, 4),
	-- 통계
	('통계' , 'statistics', 1, 5);
	
UPDATE side_menu
SET side_menu_page = 'workingBook'
WHERE side_menu_name = '작업 자료 관리';

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
	header_menu.MENU_NUM = 1;
	
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

LOAD DATA LOCAL INFILE 'C:/bookdata/insertData_2.csv'
INTO TABLE BOOK
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

