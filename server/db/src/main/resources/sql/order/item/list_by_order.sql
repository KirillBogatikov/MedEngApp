SELECT 
	"i"."id" AS "item_id", 
	"o"."id" AS "operation_id", 
	"o"."count" AS "operation_count", 
	"o"."date" AS "operation_date", 
	"p"."id" AS "product_id", 
	"p"."name" AS "product_name", 
	"p"."description" AS "product_description" 
FROM "order_item" AS "i" 
JOIN "operation" "o" ON "o"."id" = "i"."operation" 
JOIN "product" "p" ON "p"."id" = "o"."product" 
WHERE "i"."order" = ?