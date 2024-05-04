SELECT * FROM electric_accidents_by_day;
SELECT * FROM electric_accidents_by_time;

SELECT 
	SUM(sunday)/COUNT(occurred_year) ,
	SUM(monday)/COUNT(occurred_year) ,
	SUM(tuesday)/COUNT(occurred_year) ,
	SUM(wednesday)/COUNT(occurred_year) ,
	SUM(thursday)/COUNT(occurred_year) ,
	SUM(friday)/COUNT(occurred_year) ,
	SUM(saturday)/COUNT(occurred_year)
FROM 
 electric_accidents_by_day;
 
SELECT
((SELECT sunday FROM electric_accidents_by_day WHERE OCCURRED_YEAR = '2017')/

(SELECT sunday FROM electric_accidents_by_day WHERE OCCURRED_YEAR = '2016')
))*100;