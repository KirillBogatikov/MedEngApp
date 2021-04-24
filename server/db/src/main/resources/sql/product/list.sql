SELECT 
	"p"."id" AS "product_id", 
	"p"."name" AS "product_name" 
FROM "products" AS "p"  
WHERE "p"."name" ~ ? 
OFFSET ? LIMIT ?