SELECT 
	"c"."id" AS "id", 
	"c"."first_name" AS "first_name",
	"c"."last_name" AS "last_name",
	"c"."patronymic" AS "patronymic", 
	"a"."id" AS "auth_id",
	"a"."login" AS "auth_login",
	"ct"."id" AS "contact_id",
	"ct"."phone" AS "contact_phone",
	"ct"."password" AS "contact_password" 
FROM "consumer" AS "c"   
JOIN "auth" "a" ON "a"."id" = "c"."auth" 
JOIN "contact" "ct" ON "ct"."id" = "c"."contact" 
WHERE "c"."id" = ?