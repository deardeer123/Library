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