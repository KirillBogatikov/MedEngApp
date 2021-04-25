CREATE TABLE "auth" (
	"id" uuid PRIMARY KEY,
	"login" text,
	"password" text
);

CREATE TABLE "customer" (
	"id" uuid PRIMARY KEY,
	"auth" uuid REFERENCES "auth"("id") ON DELETE CASCADE, 
	"phone" text,
	"email" text,
	"first_name" text,
	"last_name" text,
	"patronymic" text
);

CREATE TYPE "employee_role" AS ENUM (
	'Operator', 'Storekeeper'
);

CREATE TABLE "employee" (
	"id" uuid PRIMARY KEY,
	"auth" uuid REFERENCES "auth"("id") ON DELETE CASCADE,
	"role" employee_role
);

CREATE TABLE "product" (
	"id" uuid PRIMARY KEY,
	"name" text NOT NULL,
	"description" text
);

CREATE TYPE "operation_type" AS ENUM (
	'Shipment', 'Booking'
);

CREATE TABLE "operation" (
	"id" uuid PRIMARY KEY,
	"product" uuid REFERENCES "product"("id") ON DELETE CASCADE,
	"count" integer,
	"type" operation_type,
	"date" timestamp
);

CREATE TABLE "order" (
	"id" uuid PRIMARY KEY,
	"customer" uuid REFERENCES "customer"("id") ON DELETE CASCADE
); 

CREATE TABLE "order_item" (
	"id" uuid PRIMARY KEY,
	"order" uuid REFERENCES "order"("id") ON DELETE CASCADE,
	"operation" uuid REFERENCES "operation"("id") ON DELETE CASCADE
);

CREATE TYPE "order_status" AS ENUM (
	'Created', 'Processing', 'Booked', 'Ready', 'Received', 'Canceled'
); 

CREATE TABLE "order_status_history" (
	"id" uuid PRIMARY KEY,
	"order" uuid REFERENCES "order"("id") ON DELETE CASCADE,
	"status" order_status,
	"date" timestamp
);