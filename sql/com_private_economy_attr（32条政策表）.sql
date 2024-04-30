CREATE TABLE "SYSDBA"."com_private_economy_attr"
(
"id" BIGINT IDENTITY(1, 1) NOT NULL,
"is_deleted" BIT DEFAULT 0 NOT NULL,
"attr_category" VARCHAR(255),
"attr_name" VARCHAR(255),
NOT CLUSTER PRIMARY KEY("id")) STORAGE(ON "MAIN", CLUSTERBTR) ;