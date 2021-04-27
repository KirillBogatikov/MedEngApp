SELECT 
	"os"."id" AS "status_id",
	"os"."order" AS "status_order_id",
	"os"."status" AS "status_status",
	"os"."date" AS "status_date"
FROM "order_status_history" "os" 
WHERE "os"."order" = ? 
ORDER BY "os"."date" DESC