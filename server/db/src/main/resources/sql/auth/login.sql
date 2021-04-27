SELECT 
	 "a"."id" AS "auth_id", 
	 "a"."login" AS "auth_login" 
FROM "auth" AS "a" 
WHERE "a"."login" = ? AND "a"."password" = ?