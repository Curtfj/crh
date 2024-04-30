CREATE TABLE "SYSDBA"."rew_batch"
(
"id" BIGINT IDENTITY(1, 1) NOT NULL,
"end_date" DATE,
"project_id" BIGINT,
"start_date" DATE,
"state" VARCHAR(50),
"is_deleted" BIT,
NOT CLUSTER PRIMARY KEY("id")) STORAGE(ON "MAIN", CLUSTERBTR) ;