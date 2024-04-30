CREATE TABLE "SYSDBA"."zczj_attr"
(
"id" INTEGER IDENTITY(1, 1) NOT NULL,
"re_id" BIGINT,
"create_by" BIGINT,
"create_time" DATETIME(6),
"update_by" BIGINT,
"update_time" DATETIME(6),
"measure" TEXT,
"situation" TEXT,
"stage" TEXT,
NOT CLUSTER PRIMARY KEY("id")) STORAGE(ON "MAIN", CLUSTERBTR) ;

COMMENT ON COLUMN "SYSDBA"."zczj_attr"."re_id" IS '源id';