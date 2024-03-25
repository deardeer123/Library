CREATE TABLE MY_EMP(
	EMP_NUM INT PRIMARY KEY AUTO_INCREMENT
	, EMP_NAME VARCHAR(10) NOT NULL
	, DEPT_NUM INT NOT NULL
	, DEPT_NAME VARCHAR(30) NOT NULL
	, RANKS VARCHAR(20) NOT NULL
);

SELECT * FROM MY_EMP;

INSERT INTO my_emp(
	EMP_NAME
	,DEPT_NUM
	,DEPT_NAME
	,RANKS
)VALUES(
	'김자바'
	,1001
	,'인사과'
	,'사원'
);


DELETE FROM my_emp
WHERE EMP_NUM = 1;

-- 부서 테이블 생성하기
CREATE TABLE DEPT(
	 DEPTNO INT PRIMARY KEY,
	 DNAME VARCHAR(14),
	 LOC   VARCHAR(13) ) ;

-- 사원 테이블 생성하기
CREATE TABLE EMP( 
  	 EMPNO INT PRIMARY KEY,
	 ENAME VARCHAR(10),
 	 JOB   VARCHAR(9),
	 MGR  INT,
	 HIREDATE DATETIME,
	 SAL INT,
	 COMM INT,
	 DEPTNO INT CONSTRAINT FK_DEPTNO REFERENCES DEPT);

 -- 급여 테이블 생성하기
CREATE TABLE SALGRADE(
	 GRADE INT,
	 LOSAL INT,
	 HISAL INT );

-- 사원 테이블에 샘플 데이터 추가하기
INSERT INTO DEPT VALUES(10, '경리부', '서울');
INSERT INTO DEPT VALUES(20, '인사부', '인천');
INSERT INTO DEPT VALUES(30, '영업부', '용인'); 
INSERT INTO DEPT VALUES(40, '전산부', '수원');

-- 부서 테이블에 샘플 데이터 추가하기
INSERT INTO EMP VALUES(1001, '김사랑', '사원', 1013, DATE_FORMAT('2007-03-01','%Y-%m-%d %H"%i%s'), 300, NULL, 20);
INSERT INTO EMP VALUES(1002, '한예슬', '대리', 1005, DATE_FORMAT('2007-04-02','%Y-%m-%d %H"%i%s'), 250,   80, 30);
INSERT INTO EMP VALUES(1003, '오지호', '과장', 1005, DATE_FORMAT('2005-02-10','%Y-%m-%d %H"%i%s'), 500,  100, 30);
INSERT INTO EMP VALUES(1004, '이병헌', '부장', 1008, DATE_FORMAT('2003-09-02','%Y-%m-%d %H"%i%s'), 600, NULL, 20);
INSERT INTO EMP VALUES(1005, '신동협', '과장', 1005, DATE_FORMAT('2005-04-07','%Y-%m-%d %H"%i%s'), 450,  200, 30);
INSERT INTO EMP VALUES(1006, '장동건', '부장', 1008, DATE_FORMAT('2003-10-09','%Y-%m-%d %H"%i%s'), 480, NULL, 30);
INSERT INTO EMP VALUES(1007, '이문세', '부장', 1008, DATE_FORMAT('2004-01-08','%Y-%m-%d %H"%i%s'), 520, NULL, 10);
INSERT INTO EMP VALUES(1008, '감우성', '차장', 1003, DATE_FORMAT('2004-03-08','%Y-%m-%d %H"%i%s'), 500,    0, 30);
INSERT INTO EMP VALUES(1009, '안성기', '사장', NULL, DATE_FORMAT('1996-10-04','%Y-%m-%d %H"%i%s'),1000, NULL, 20);
INSERT INTO EMP VALUES(1010, '이병헌', '과장', 1003, DATE_FORMAT('2005-04-07','%Y-%m-%d %H"%i%s'), 500, NULL, 10);
INSERT INTO EMP VALUES(1011, '조향기', '사원', 1007, DATE_FORMAT('2007-03-01','%Y-%m-%d %H"%i%s'), 280, NULL, 30);
INSERT INTO EMP VALUES(1012, '강혜정', '사원', 1006, DATE_FORMAT('2007-08-09','%Y-%m-%d %H"%i%s'), 300, NULL, 20);
INSERT INTO EMP VALUES(1013, '박중훈', '부장', 1003, DATE_FORMAT('2002-10-09','%Y-%m-%d %H"%i%s'), 560, NULL, 20);
INSERT INTO EMP VALUES(1014, '조인성', '사원', 1006, DATE_FORMAT('2007-11-09','%Y-%m-%d %H"%i%s'), 250, NULL, 10);

-- 급여 테이블에 샘플 데이터 추가하기
INSERT INTO SALGRADE VALUES (1, 700,1200);
INSERT INTO SALGRADE VALUES (2, 1201,1400);
INSERT INTO SALGRADE VALUES (3, 1401,2000);
INSERT INTO SALGRADE VALUES (4, 2001,3000);
INSERT INTO SALGRADE VALUES (5, 3001,9999);
COMMIT;

SELECT * FROM emp;

-- 4. 급여가 500이하이거나 1500이상인 사원 중에서 커미션이 NULL이 아닌 사원의 사원번호, 사원명, 급여, 커미션을 조회하는 쿼리문을 작성하세요.
SELECT
	EMPNO
	,ENAME
	,SAL
	,COMM
FROM emp
WHERE (SAL <= 500 OR SAL >= 1500)
AND COMM IS NOT NULL;

-- 5. 사원들 중 이름이 ‘이’로 시작하는 사원들의 사번, 이름, 입사일을 조회하되, 사번기준 오름차순 정렬하여 조회하는 쿼리문을 작성하세요
SELECT
	EMPNO
	,ENAME
	,HIREDATE
FROM emp
WHERE ENAME LIKE '이%'
ORDER BY EMPNO ASC;

-- 6. 사원의 사번, 이름, 부서번호, 부서명을 조회해보자. 부서명은 부서번호가 10일 때는 ‘인사부’, 
-- 20일 때는 ‘영업부’ 그 외의 값은 ‘생산부’로 조회되어야 한다. CASE 함수를 사용하여 쿼리문을 작성해보자

SELECT 
	EMPNO
	,ENAME
	,DNAME
	,
	CASE
		WHEN E.DEPTNO = 10 THEN '인사부' 
		WHEN E.DEPTNO = 20 THEN '영업부' 
		ELSE '생산부'
	END AS '부서'
FROM emp as E INNER JOIN dept as D
ON E.DEPTNO = D.DEPTNO;

SELECT * FROM dept;

-- 7. 2007년에 입사한 모든 사원의 사번, 이름, 입사일, 커미션을 입사일 기준 오름차순으로 조회하는 쿼리문을 작성하세요
SELECT 
	EMPNO 사번
	,ENAME 이름
	,HIREDATE 입사일
	,COMM
FROM emp
WHERE DATE_FORMAT(HIREDATE , '%Y') = 2007
ORDER BY HIREDATE ASC;


-- 8. 직급별로 그룹지어서 직급별 급여의 합과 급여의 평균, 커미션의 평균을 조회하는 쿼리문을 작성하세요.
-- 단 조회는 직급 기준 오름차순으로 정렬하여 나타내세요
SELECT
	JOB
	,SUM(SAL)
	,AVG(SAL)
	,IFNULL(AVG(COMM), 0.0)
FROM emp
GROUP BY JOB
ORDER BY ASC;

-- 9. 서브쿼리를 사용하여 지역이 ‘서울’인 사원의 사번, 이름, 입사일, 급여, 부서번호, 지역명을 조회하는 쿼리문을 작성해보자
SELECT
	EMPNO
	,ENAME
	,HIREDATE
	,SAL
	,DEPTNO
	,(SELECT LOC FROM DEPT WHERE LOC = '서울') AS LOC
FROM emp
WHERE	DEPTNO = (SELECT DEPTNO FROM DEPT WHERE LOC = '서울');



-- 10. 조인을 지역이 ‘서울’인 사원의 사번, 이름, 입사일, 급여, 부서번호, 지역명을 조회하는 쿼리문을 작성해보자
SELECT
	EMPNO
	,ENAME
	,HIREDATE
	,SAL
	,E.DEPTNO
	,LOC
FROM emp AS E 
INNER JOIN dept AS D
ON E.DEPTNO = D.DEPTNO
WHERE LOC = (SELECT LOC FROM DEPT WHERE LOC = '서울');



-- group by 통계 쿼리에서 사용
-- 직급별로 그룹지어서 직급별 급여의 합과 급여의 평균, 커미션의 평균을 조회하는 쿼리문을 작성하세요. 
-- 단 조회는 직급 기준 오름차순으로 정렬하여 나타내세요
-- 만약 커미션의 평균이 NULL이라면 0.0으로 조회
SELECT
	JOB
	, SUM(SAL)
	, AVG(SAL)
	, IFNULL (AVG(COMM), 0.0)
FROM emp 
GROUP BY JOB
ORDER BY JOB;

SELECT * FROM emp;

-- 1월에 입사한 1월의 입사한 사원들을 제외한  급여의 합 조회
SELECT -- 3
	DATE_FORMAT(HIREDATE, '%m') 입사월 
	,COUNT(EMPNO)
FROM emp -- 1
WHERE DATE_FORMAT(HIREDATE, '%m') != 01 -- 2
GROUP BY DATE_FORMAT(HIREDATE, '%m') -- 4
ORDER BY ASC; -- 5

SELECT 
	HIREDATE
	,SUBSTRING(HIREDATE, 6, 2)
	,DATE_FORMAT(HIREDATE , '%m')
FROM emp;

-- 월별 입사자 수를 조회
-- 월별 입사자 수가 두명 이상인 데이터 조회
-- 조회시 월별 입사자 수가 높은 순으로 조회
SELECT
	DATE_FORMAT(HIREDATE , '%m') 입사월
	,COUNT(EMPNO) 입사인원
FROM emp
WHERE DATE_FORMAT(HIREDATE , '%m') != '10'
GROUP BY 입사월
HAVING 입사인원 >= 2
ORDER BY 입사인원 DESC;	-- 가장 마지막으로 해석

-- 직원들의 급여의 합 조회
SELECT 
	SUM(SAL)
FROM emp;

-- 각 직급별 급여의 합
SELECT 
	JOB
	,SUM(SAL)
FROM emp
GROUP BY JOB;

-- 중복을 제거한 데이터 조회
SELECT DISTINCT JOB FROM emp;

-- 부서번호별 인원 수 조회
SELECT 
	DEPTNO
	,COUNT(EMPNO)
FROM emp
GROUP BY DEPTNO;
	
	
-- 다중 행 함수 : 데이터의 개수오 상관없이 조회 결과가 1행 나오는 함수

-- EX) SUM(), COUNT(), MIN(), MAX(), AVG() 등
-- 사번, 사원명 ,모든 직원의 급여의 합 조회
SELECT
	


