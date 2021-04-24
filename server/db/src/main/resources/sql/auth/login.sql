SELECT 
	 "a"."id" AS "auth_id" 
FROM "auth" AS "a" 
WHERE "a"."login" = ? AND "a"."password" = ?