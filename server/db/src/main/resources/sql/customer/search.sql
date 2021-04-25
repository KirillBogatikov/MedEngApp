SELECT 
	"c"."id" AS "id", 
	"c"."first_name" AS "first_name",
	"c"."last_name" AS "last_name",
	"c"."patronymic" AS "patronymic",
	"c"."phone" AS "phone",
	"c"."email" AS "email", 
	"a"."id" AS "auth_id",
	"a"."login" AS "auth_login"
FROM "consumer" AS "c"   
JOIN "auth" "a" ON "a"."id" = "c"."auth" 
WHERE "c"."first_name" ~ ? OR "c"."last_name" ~ ? OR "c"."patronymic" ~ ? OR
	  "a"."login" ~ ? OR "c"."phone" ~ ? OR "c"."email" ~ ?