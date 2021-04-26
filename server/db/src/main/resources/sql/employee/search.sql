SELECT 
	"a"."id" AS "auth_id",
	"a"."login" AS "auth_login",
	"e"."id" AS "employee_id",
	"e"."role" AS "employee_role" 
FROM "employee" AS "e" 
JOIN "auth" "a" ON "a"."id" = "e"."auth" 
WHERE "a"."login" ~ ?