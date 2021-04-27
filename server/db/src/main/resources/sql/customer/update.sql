UPDATE "auth" SET "login" = ?, "password" = ? WHERE "id" = ?; 
UPDATE "customer" SET 
	"first_name" = ?,
	"last_name" = ?,
	"patronymic" = ?,
	"phone" = ?,
	"email" = ?
WHERE "id" = ?;