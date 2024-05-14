SELECT * FROM electric_accidents_by_day;
SELECT * FROM electric_accidents_by_time;

SELECT 
	round(SUM(sunday)/COUNT(occurred_year),1)AS sunday ,
	round(SUM(monday)/COUNT(occurred_year),1)AS monday ,
	round(SUM(tuesday)/COUNT(occurred_year),1)AS tuesday ,
	round(SUM(wednesday)/COUNT(occurred_year),1)AS wednesday ,
	round(SUM(thursday)/COUNT(occurred_year),1)AS thurday ,
	round(SUM(friday)/COUNT(occurred_year),1)AS friday ,
	round(SUM(saturday)/COUNT(occurred_year),1)AS saturday
FROM 
 electric_accidents_by_day;
 
SELECT
((SELECT sunday FROM electric_accidents_by_day WHERE OCCURRED_YEAR = '2017')/

(SELECT sunday FROM electric_accidents_by_day WHERE OCCURRED_YEAR = '2016')
))*100;

SELECT * FROM electric_accidents_by_day;
SELECT * FROM electric_accidents_by_time;

SELECT
	round(SUM(HOUR00_02)/COUNT(occurred_year),1) AS HOUR00_02,
	round(SUM(HOUR02_04)/COUNT(occurred_year),1) AS HOUR02_04,
	round(SUM(HOUR04_06)/COUNT(occurred_year),1) AS HOUR04_06,
	round(SUM(HOUR06_08)/COUNT(occurred_year),1) AS HOUR06_08,
	round(SUM(HOUR08_10)/COUNT(occurred_year),1) AS HOUR08_10,
	round(SUM(HOUR10_12)/COUNT(occurred_year),1) AS HOUR10_12,
	round(SUM(HOUR12_14)/COUNT(occurred_year),1) AS HOUR12_14,
	round(SUM(HOUR14_16)/COUNT(occurred_year),1) AS HOUR14_16,
	round(SUM(HOUR16_18)/COUNT(occurred_year),1) AS HOUR16_18,
	round(SUM(HOUR18_20)/COUNT(occurred_year),1) AS HOUR18_20 ,
	round(SUM(HOUR20_22)/COUNT(occurred_year),1) AS HOUR20_22 ,
	round(SUM(HOUR22_24)/COUNT(occurred_year),1) AS HOUR22_24 
FROM 
	electric_accidents_by_time
	ORDER BY
	
SELECT
	SUM(HOUR00_02) AS HOUR00_02,
	SUM(HOUR02_04) AS HOUR02_04,
	SUM(HOUR04_06) AS HOUR04_06,
	SUM(HOUR06_08) AS HOUR06_08,
	SUM(HOUR08_10) AS HOUR08_10,
	SUM(HOUR10_12) AS HOUR10_12,
	SUM(HOUR12_14) AS HOUR12_14,
	SUM(HOUR14_16) AS HOUR14_16,
	SUM(HOUR16_18) AS HOUR16_18,
	SUM(HOUR18_20) AS HOUR18_20,
	SUM(HOUR20_22) AS HOUR20_22,
	SUM(HOUR22_24) AS HOUR22_24
FROM 
	electric_accidents_by_time;
	
SELECT 
             sum(SEOUL) AS '서울'
            , sum(BUSAN) AS BUSAN
            , sum(DAEGU) AS DAEGU
            , sum(INCHEON) AS INCHEON
            , sum(GWANGJU) AS GWANGJU
            , sum(DAEJEON) AS DAEJEON
            , sum(SEJONG) AS SEJONG
            , sum(ULSAN) AS ULSAN
            , sum(GYEONGGI) AS GYEONGGI
            , sum(GANGWON) AS GANGWON
            , sum(CHUNGBUK) AS CHUNGBUK
            , sum(CHUNGNAM) AS CHUNGNAM
            , sum(JEONBUK) AS JEONBUK
            , sum(JEONNAM) AS JEONNAM
            , sum(GYEONGNUK) AS GYEONGNUK
            , sum(GYEONGNAM) AS GYEONGNAM
            , sum(JEJU) AS  JEJU
        FROM electric_accidents_fire_statistics;
	
SELECT
occurred_year
FROM electric_accidents_fire_statistics;
SELECT

		   round(sum(SEOUL)/COUNT(occurred_year),1) AS '서울'
        , round(sum(BUSAN)/COUNT(occurred_year),1) AS '부산'
        , round(sum(DAEGU)/COUNT(occurred_year),1) AS '대구'
        , round(sum(INCHEON)/COUNT(occurred_year),1) AS '인천'
        , round(sum(GWANGJU)/COUNT(occurred_year),1) AS '광주'
        , round(sum(DAEJEON)/COUNT(occurred_year),1) AS '대전'
        , round(sum(SEJONG)/COUNT(occurred_year),1) AS '세종'
        , round(sum(ULSAN)/COUNT(occurred_year),1) AS '울산'
        , round(sum(GYEONGGI)/COUNT(occurred_year),1) AS '경기도'
        , round(sum(GANGWON)/COUNT(occurred_year),1) AS '강원도'
        , round(sum(CHUNGBUK)/COUNT(occurred_year),1) AS '충북'
        , round(sum(CHUNGNAM)/COUNT(occurred_year),1) AS '충남'
        , round(sum(JEONBUK)/COUNT(occurred_year),1) AS '전북'
        , round(sum(JEONNAM)/COUNT(occurred_year),1) AS '전남'
        , round(sum(GYEONGNUK)/COUNT(occurred_year),1) AS '경북'
        , round(sum(GYEONGNAM)/COUNT(occurred_year),1) AS '경남'
        , round(sum(JEJU)/COUNT(occurred_year),1) AS '제주'
        FROM ELECTRIC_ACCIDENTS_FIRE_STATISTICS
        WHERE 1 = 1;
	
SELECT occurred_year, GYEONGNUK

FROM 

electric_accidents_fire_statistics
ORDER BY occurred_year;
select
        occurred_year,
        GYEONGNUK
        FROM
        electric_accidents_fire_statistics
        ORDER BY occurred_year;
        
SELECT 
	round(SUM(total_fire)/COUNT(occurred_year),0) AS TOTAL_FIRE,
	round(SUM(e_fire)/COUNT(occurred_year),0) AS E_FIRE,
	round(SUM(dead)/COUNT(occurred_year),0) AS DEAD,
	round(SUM(ouch)/COUNT(occurred_year),0) AS OUCH,
	round(SUM(money_damage)/COUNT(occurred_year),0) AS MONEY_DAMAGE
FROM
 e_disaster;

        SELECT
        round(SUM(HOUR00_02)/COUNT(occurred_year),1) AS HOUR00_02,
        round(SUM(HOUR02_04)/COUNT(occurred_year),1) AS HOUR02_04,
        round(SUM(HOUR04_06)/COUNT(occurred_year),1) AS HOUR04_06,
        round(SUM(HOUR06_08)/COUNT(occurred_year),1) AS HOUR06_08,
        round(SUM(HOUR08_10)/COUNT(occurred_year),1) AS HOUR08_10,
        round(SUM(HOUR10_12)/COUNT(occurred_year),1) AS HOUR10_12,
        round(SUM(HOUR12_14)/COUNT(occurred_year),1) AS HOUR12_14,
        round(SUM(HOUR14_16)/COUNT(occurred_year),1) AS HOUR14_16,
        round(SUM(HOUR16_18)/COUNT(occurred_year),1) AS HOUR16_18,
        round(SUM(HOUR18_20)/COUNT(occurred_year),1) AS HOUR18_20 ,
        round(SUM(HOUR20_22)/COUNT(occurred_year),1) AS HOUR20_22 ,
        round(SUM(HOUR22_24)/COUNT(occurred_year),1) AS HOUR22_24
        FROM
        electric_accidents_by_time;
        
SELECT TOTAL_NUMBER_OF_ACCIDENTS 
FROM current_status_of_electric_accidents
WHERE OCCURRED_YEAR = 2022;