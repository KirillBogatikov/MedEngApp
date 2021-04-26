SELECT 
	max("s"."status") AS "status" 
FROM "order_status_history" "s" 
WHERE "s"."order" = ?