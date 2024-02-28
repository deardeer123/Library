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
	
	
DROP TABLE users;


CREATE TABLE BOARD(
  BOARD_NO INT AUTO_INCREMENT PRIMARY KEY
  , BOARD_TITLE VARCHAR(50) NOT NULL
  , CONTENT VARCHAR(200)
  , BOARD_DATE DATETIME 
  , USER_CODE INT REFERENCES USERS(USER_CODE) 
  , BOARD_CNT INT
);
DROP TABLE board;

-- 2024-02-28 
CREATE TABLE CUL_BOARD(
  BOARD_NO INT AUTO_INCREMENT PRIMARY KEY
  , BOARD_TITLE VARCHAR(50) NOT NULL
  , CONTENT VARCHAR(200)
  , BOARD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
  , USER_CODE INT REFERENCES USERS(USER_CODE) 
  , BOARD_CNT INT
);


