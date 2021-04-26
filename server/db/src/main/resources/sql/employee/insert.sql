INSERT INTO "auth" ("id", "login", "password")  
	VALUES (?, ?, ?); 
INSERT INTO "employee" ("id", "auth", "role") 
	VALUES (?, ?, ?);