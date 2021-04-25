INSERT INTO "auth" ("id", "login", "password")  
	VALUES (?, ?, ?); 
INSERT INTO "customer" 
	("id", "auth", "first_name", "last_name", "patronymic", "phone", "email")
VALUES 
	(?, ?, ?, ?, ?, ?, ?);