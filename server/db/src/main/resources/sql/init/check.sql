SELECT 
    1
FROM
    "information_schema"."tables" as "tables"  
where
	"tables"."table_schema" = ?;