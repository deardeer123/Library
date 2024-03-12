CREATE TABLE CAR_INFO(
	CAR_CODE INT PRIMARY KEY AUTO_INCREMENT
	,CAR_NAME VARCHAR(50) NOT NULL
	,CAR_PRICE VARCHAR(50) NOT NULL
	,COMPANY VARCHAR(50) NOT NULL
);

DROP TABLE car_info;

CREATE TABLE SALES_INFO(
	SALES_CODE INT PRIMARY KEY AUTO_INCREMENT
	,BUYER_NAME VARCHAR(50) NOT NULL
	,BUYER_TEL VARCHAR(50) NOT NULL
	,COLOR VARCHAR(50) NOT NULL
	,BUY_DATE DATETIME DEFAULT CURRENT_TIMESTAMP
	,CAR_CODE INT REFERENCES CAR_INFO (CAR_CODE)
);


SELECT * FROM sales_info;

SELECT
            CAR.CAR_CODE
            ,BUYER_NAME
            ,BUYER_TEL
            ,BUY_DATE
            ,COLOR
            ,CAR_PRICE
            ,CAR_NAME
            ,SALES_CODE
        FROM CAR_INFO CAR
        INNER JOIN SALES_INFO SALES
        ON CAR.CAR_CODE = SALES.CAR_CODE;