SELECT 
	"c"."id" AS "customer_id", 
	"c"."first_name" AS "customer_first_name",
	"c"."last_name" AS "customer_last_name",
	"c"."patronymic" AS "customer_patronymic",
	"c"."phone" AS "customer_phone",
	"c"."email" AS "customer_email", 
	"a"."id" AS "auth_id",
	"a"."login" AS "auth_login" 
FROM "customer" AS "c" 
JOIN "auth" "a" ON "a"."id" = "c"."auth"  
WHERE "c"."id" = ?