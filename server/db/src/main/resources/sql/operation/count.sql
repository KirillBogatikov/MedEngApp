SELECT 
	SUM("o"."count") AS "count" 
FROM "operation" "o" 
WHERE 
	"o"."product" = ? AND 
	"o"."type" = ?;