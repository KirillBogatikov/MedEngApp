SELECT 
	"p"."id" AS "product_id", 
	"p"."name" AS "product_name",
	"p"."description" AS "product_description" 
FROM "product" AS "p"  
WHERE "p"."id" = ?