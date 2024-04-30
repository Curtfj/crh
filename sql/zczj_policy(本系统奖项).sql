CREATE TABLE "SYSDBA"."zczj_policy"
(
"id" INTEGER IDENTITY(99000, 1) NOT NULL,
"policy_name" VARCHAR(8188) NOT NULL,
"policy_content" TEXT,
"hq_category_id" INTEGER NOT NULL,
"supervisor_phone" VARCHAR(8188),
"sponsor" VARCHAR(8188),
"address" VARCHAR(8188),
"create_by" INTEGER,
"create_time" DATETIME(6),
"update_by" INTEGER,
"update_time" DATETIME(6),
"eddective_date" DATETIME(6),
"deadline" DATETIME(6),
CONSTRAINT "zczj_policy_pk" NOT CLUSTER PRIMARY KEY("id")) STORAGE(ON "MAIN", CLUSTERBTR) ;