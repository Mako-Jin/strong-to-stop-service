/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 100017
 Source Host           : localhost:5432
 Source Catalog        : strong-to-stop
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100017
 File Encoding         : 65001

 Date: 10/04/2022 15:56:11
*/


-- ----------------------------
-- Table structure for smk_tbl_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_file";
CREATE TABLE "public"."smk_tbl_file" (
  "file_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "file_name" varchar(64) COLLATE "pg_catalog"."default",
  "file_type" varchar(128) COLLATE "pg_catalog"."default",
  "file_size" int8,
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "is_delete" int2,
  "identifier" varchar(128) COLLATE "pg_catalog"."default",
  "progress" float4
)
;
COMMENT ON COLUMN "public"."smk_tbl_file"."is_delete" IS '1:是 2：否';

-- ----------------------------
-- Records of smk_tbl_file
-- ----------------------------
INSERT INTO "public"."smk_tbl_file" VALUES ('b9e731d951fb48f7920bc99edff2e67e', '849b4d0d89ff4a9b9926dd6e20f7becf', 'image/jpeg', 78760, 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-07 16:55:40.256', '2022-04-07 16:55:40.256', 2, 'c4096f64f9d2cc598eb9ce10dc1f079e', 100);
INSERT INTO "public"."smk_tbl_file" VALUES ('1c35013d69184b69a996dd8eca34174d', '449a0aeba63b43bd8e49dee41782cfd0', 'image/jpeg', 1595907, 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-08 10:43:33.542', '2022-04-08 10:43:33.542', 2, '45db21cba316888cfbac8ca82caa4a9c', 100);
INSERT INTO "public"."smk_tbl_file" VALUES ('6553f6b3929e4452a657afee4a5b7359', '29254b9988d3468cb42bdd3e80731f6f', 'image/png', 245332, 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-08 19:37:07.82', '2022-04-08 19:37:07.82', 2, '3ef06d3b5ab7df727b54c55ca1236f1b', 100);

-- ----------------------------
-- Table structure for smk_tbl_file_catalog
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_file_catalog";
CREATE TABLE "public"."smk_tbl_file_catalog" (
  "catalog_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "file_id" varchar(64) COLLATE "pg_catalog"."default",
  "original_filename" varchar(255) COLLATE "pg_catalog"."default",
  "relative_path" varchar(255) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "parent_id" varchar(64) COLLATE "pg_catalog"."default",
  "is_directory" int2,
  "is_delete" int2,
  "file_type" varchar(128) COLLATE "pg_catalog"."default",
  "file_size" int8
)
;
COMMENT ON COLUMN "public"."smk_tbl_file_catalog"."original_filename" IS '原始文件名';
COMMENT ON COLUMN "public"."smk_tbl_file_catalog"."is_delete" IS '1:是 2：否';

-- ----------------------------
-- Records of smk_tbl_file_catalog
-- ----------------------------
INSERT INTO "public"."smk_tbl_file_catalog" VALUES ('19e6855b8f72404e93f5ff1fbf645776', '1c35013d69184b69a996dd8eca34174d', '1.jpg', 'file_top_parent_id', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-08 10:43:33.618', '2022-04-08 18:56:50.523', '', 2, 2, 'image/jpeg', 1595907);
INSERT INTO "public"."smk_tbl_file_catalog" VALUES ('08e310fa4d514d158be040fe1980ba44', '6553f6b3929e4452a657afee4a5b7359', 'druid.png', 'file_top_parent_id', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-08 19:37:07.832', '2022-04-08 19:37:07.832', '', 2, 2, 'image/png', 245332);
INSERT INTO "public"."smk_tbl_file_catalog" VALUES ('7104bc7451c64ddf8e5b1e24f3fdc02c', 'b9e731d951fb48f7920bc99edff2e67e', 'avatar.jpg', 'file_top_parent_id', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '2022-04-07 16:55:40.319', '2022-04-07 16:55:40.319', 'file_top_parent_id', 2, 2, 'image/jpeg', 78760);

-- ----------------------------
-- Table structure for smk_tbl_file_slice
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_file_slice";
CREATE TABLE "public"."smk_tbl_file_slice" (
  "slice_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2,
  "file_id" varchar(64) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "identifier" varchar(255) COLLATE "pg_catalog"."default",
  "chunk_number" int4,
  "chunk_size" int8
)
;

-- ----------------------------
-- Records of smk_tbl_file_slice
-- ----------------------------
INSERT INTO "public"."smk_tbl_file_slice" VALUES ('5f941210f534485ca6cdaf230cc44d48', 3, 'b9e731d951fb48f7920bc99edff2e67e', 'c09117a2931f41998633095f8228829d', '2022-04-07 16:55:40.273', 'c09117a2931f41998633095f8228829d', '2022-04-07 16:55:40.527', 'c4096f64f9d2cc598eb9ce10dc1f079e', 1, 78760);
INSERT INTO "public"."smk_tbl_file_slice" VALUES ('74963e6c921a424abe9fc2058f0f88f5', 3, '1c35013d69184b69a996dd8eca34174d', 'c09117a2931f41998633095f8228829d', '2022-04-08 10:43:33.571', 'c09117a2931f41998633095f8228829d', '2022-04-08 10:43:33.856', '45db21cba316888cfbac8ca82caa4a9c', 1, 1595907);
INSERT INTO "public"."smk_tbl_file_slice" VALUES ('abeefd389d714550b00eb6ed770d27df', 3, '6553f6b3929e4452a657afee4a5b7359', 'c09117a2931f41998633095f8228829d', '2022-04-08 19:37:07.824', 'c09117a2931f41998633095f8228829d', '2022-04-08 19:37:07.97', '3ef06d3b5ab7df727b54c55ca1236f1b', 1, 245332);

-- ----------------------------
-- Table structure for smk_tbl_rela_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_rela_role_menu";
CREATE TABLE "public"."smk_tbl_rela_role_menu" (
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of smk_tbl_rela_role_menu
-- ----------------------------
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('2', '7e2439a81eb04c5da22d1c825bee7076', NULL, NULL, NULL, NULL, 1);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('2', 'bb1a674789f24ba3aa0052d37f1f28f8', NULL, NULL, NULL, NULL, 2);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '4bae852f730e4aaf8c071c4e8c03ac34', NULL, NULL, NULL, NULL, 3);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'f43e063845824fcba3bc20d12aeaa68c', NULL, NULL, NULL, NULL, 4);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'db371c84f50f4a8da02b52e2d02fbe11', NULL, NULL, NULL, NULL, 5);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'ff0b6d05252a4f48aa2d22e5e6e8d219', NULL, NULL, NULL, NULL, 6);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '326a02fd93f24990bef226f4d1d79b9d', NULL, NULL, NULL, NULL, 7);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'd2458831b3b945849e37edf6bb809fa2', NULL, NULL, NULL, NULL, 8);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '49eb31e4057747e2980d5f33ad8a1870', NULL, NULL, NULL, NULL, 9);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '04e7a9f5a8ee440a91f3f82da6538c97', NULL, NULL, NULL, NULL, 10);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '3f5872def16a4817bd946b85b08ff0ea', NULL, NULL, NULL, NULL, 11);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '9b2e4be37fab41e5a49098de363750eb', NULL, NULL, NULL, NULL, 12);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '3968ebf5a5ef468eb82899ce6a2113bc', NULL, NULL, NULL, NULL, 13);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'be4811cec78a406eb5b8ad4c4e5698bb', NULL, NULL, NULL, NULL, 14);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'd8dcf47996a14878b6a5e4ac34648754', NULL, NULL, NULL, NULL, 15);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', '0132ae9360f44afabe5ac25fcc901829', NULL, NULL, NULL, NULL, 16);
INSERT INTO "public"."smk_tbl_rela_role_menu" VALUES ('1', 'e3245778226740bf9a99f09d4f9410d6', NULL, NULL, NULL, NULL, 17);

-- ----------------------------
-- Table structure for smk_tbl_rela_role_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_rela_role_user";
CREATE TABLE "public"."smk_tbl_rela_role_user" (
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of smk_tbl_rela_role_user
-- ----------------------------
INSERT INTO "public"."smk_tbl_rela_role_user" VALUES ('1', 'c09117a2931f41998633095f8228829d', NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for smk_tbl_storage
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_storage";
CREATE TABLE "public"."smk_tbl_storage" (
  "storage_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default",
  "used_storage_size" int8,
  "total_storage_size" int8,
  "upgrade_storage_size" int8,
  "is_delete" int2,
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."smk_tbl_storage"."is_delete" IS '是否释放 1:是，2：否';

-- ----------------------------
-- Records of smk_tbl_storage
-- ----------------------------

-- ----------------------------
-- Table structure for smk_tbl_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_sys_menu";
CREATE TABLE "public"."smk_tbl_sys_menu" (
  "menu_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_name" varchar(64) COLLATE "pg_catalog"."default",
  "menu_url" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_status" int2 NOT NULL DEFAULT 1,
  "menu_desc" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int2,
  "is_secret" int2 NOT NULL DEFAULT 1,
  "menu_type" int2,
  "menu_level" int4,
  "parent_id" varchar(64) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."smk_tbl_sys_menu"."menu_url" IS '接口地址';
COMMENT ON COLUMN "public"."smk_tbl_sys_menu"."menu_desc" IS '接口描述';
COMMENT ON COLUMN "public"."smk_tbl_sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "public"."smk_tbl_sys_menu"."is_secret" IS '1：是；2：否';
COMMENT ON TABLE "public"."smk_tbl_sys_menu" IS '文件元数据';

-- ----------------------------
-- Records of smk_tbl_sys_menu
-- ----------------------------
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('db371c84f50f4a8da02b52e2d02fbe11', '新建文件夹', '/catalogMgr/v1/createDirectory', 1, '新建文件夹', 212, 2, 3, 3, '06340d090bac4116a7b90096c77c69ea', 'c09117a2931f41998633095f8228829d', '2022-03-17 18:36:09.066', 'c09117a2931f41998633095f8228829d', '2022-03-17 18:36:09.066');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('ea05901cd30e495d884cafac297f37a8', '新增角色', '/roleMgr/v1/saveRole', 1, '新增角色', 132, 2, 3, 3, 'b1d0150ed6fb4077a01e3b637617d1a2', 'c09117a2931f41998633095f8228829d', '2022-02-15 19:29:36.844', 'c09117a2931f41998633095f8228829d', '2022-02-15 19:29:36.844');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('a7abab9260a74e148426f5ef4e163d18', '删除菜单', '/menuMgr/v1/deleteMenuByMenuId/**', 1, '根据Id删除菜单', 124, 2, 3, 3, '0408d6b35bd64bfab1df79e39d4ad9a0', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:19:27.741', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:19:27.741');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('b1d0150ed6fb4077a01e3b637617d1a2', '角色管理', '/roleMgr/v1/**', 2, '角色管理菜单', 130, 2, 2, 2, 'c7cbbba1eac64ec79890e174b07e7adb', 'c09117a2931f41998633095f8228829d', '2022-02-15 19:28:32.977', 'c09117a2931f41998633095f8228829d', '2022-02-15 19:28:32.977');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('8be1c2cc11ce432ea2dc5104fa53e364', '更新菜单', '/menuMgr/v1/updateMenuByMenuId', 1, '根据ID更新菜单', 123, 2, 3, 3, '0408d6b35bd64bfab1df79e39d4ad9a0', 'c09117a2931f41998633095f8228829d', '2022-02-14 09:52:35.951', 'c09117a2931f41998633095f8228829d', '2022-02-14 09:52:35.951');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('4bae852f730e4aaf8c071c4e8c03ac34', '获取菜单列表', '/menuMgr/v1/getMenuTreeList', 1, '获取菜单树列表', 121, 1, 3, 3, '0408d6b35bd64bfab1df79e39d4ad9a0', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:20:54.482', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:20:54.482');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('0132ae9360f44afabe5ac25fcc901829', '删除用户', '/userMgr/v1/deleteUserByUserId/**', 1, '根据Id删除用户', 114, 2, 3, 3, 'b7984ae1480142638b35400ff79d49a0', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:43:40.168', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:43:40.168');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('3968ebf5a5ef468eb82899ce6a2113bc', '分页获取用户数据', '/userMgr/v1/getUserPageList/**', 1, '分页获取用户信息', 111, 2, 3, 3, 'b7984ae1480142638b35400ff79d49a0', 'c09117a2931f41998633095f8228829d', '2022-02-10 16:32:14.467', 'c09117a2931f41998633095f8228829d', '2022-02-10 16:32:14.467');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('d2458831b3b945849e37edf6bb809fa2', '删除文件夹或文件', '/catalogMgr/v1/deleteByCatalogId/*', 1, '根据id删除文件夹或文件', 214, 2, 3, 3, '06340d090bac4116a7b90096c77c69ea', 'c09117a2931f41998633095f8228829d', '2022-03-18 19:09:31.492', 'c09117a2931f41998633095f8228829d', '2022-03-18 19:09:31.492');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('561382a2f4e4486aae42a51016d68a92', '文件网盘', '', 2, '网盘功能', 200, 2, 1, 1, 'STS_TREE_TOP_ID', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:10:09.061', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:17:47.804');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('0408d6b35bd64bfab1df79e39d4ad9a0', '菜单管理', '/menuMgr/v1/**', 2, '菜单管理页面', 120, 2, 2, 2, 'c7cbbba1eac64ec79890e174b07e7adb', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:14:02.685', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:14:02.685');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('246fabe3cc0645248fbfe7e130ea44ce', '文件管理', '/fileMgr/v1/**', 2, '文件管理', 220, 2, 2, 2, '561382a2f4e4486aae42a51016d68a92', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('c7cbbba1eac64ec79890e174b07e7adb', '系统管理', '', 2, '系统管理', 100, 2, 1, 1, 'STS_TREE_TOP_ID', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:18:13.128', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:18:13.128');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('b7984ae1480142638b35400ff79d49a0', '用户管理', '/userMgr/v1/**', 2, '用户管理', 110, 2, 2, 2, 'c7cbbba1eac64ec79890e174b07e7adb', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:14:02.685', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:14:02.685');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('fa4357a946024eb39d7556d9d5f8cc8a', '文件传输', '/transfer/v1/**', 2, '文件传输，上传下载，预览', 240, 2, 2, 2, '561382a2f4e4486aae42a51016d68a92', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:18:13.516', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:18:13.517');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('ff0b6d05252a4f48aa2d22e5e6e8d219', '重命名文件夹', '/catalogMgr/v1/renameCatalog', 1, '重命名文件夹或文件', 213, 2, 3, 3, '06340d090bac4116a7b90096c77c69ea', 'c09117a2931f41998633095f8228829d', '2022-03-18 09:40:33.353', 'c09117a2931f41998633095f8228829d', '2022-03-18 09:40:33.353');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('3f5872def16a4817bd946b85b08ff0ea', '检查文件标识', '/transfer/v1/checkIdentifier', 1, '文件秒传，检查文件状态', 241, 2, 3, 3, 'fa4357a946024eb39d7556d9d5f8cc8a', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:22:06.59', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:22:06.59');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('49eb31e4057747e2980d5f33ad8a1870', '文件上传', '/transfer/v1/upload', 1, '文件上传', 242, 2, 3, 3, 'fa4357a946024eb39d7556d9d5f8cc8a', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:21:02.051', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:21:02.051');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('0cae034532ff498a8e7f765a4dc93ec0', '删除角色', '/roleMgr/v1/deleteRoleByRoleId/**', 1, '根据Id删除角色', 134, 2, 3, 3, 'b1d0150ed6fb4077a01e3b637617d1a2', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:17:47.804', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:17:47.804');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('49382004edc8467fb87caa570fff8d21', '更新角色', '/roleMgr/v1/updateRole', 1, '根据Id更新角色', 133, 2, 3, 3, 'b1d0150ed6fb4077a01e3b637617d1a2', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:07:22.637', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:07:22.637');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('04e7a9f5a8ee440a91f3f82da6538c97', '初始化文件', '/fileMgr/v1/initiateFile', 1, '初始化文件', 221, 2, 3, 3, '246fabe3cc0645248fbfe7e130ea44ce', 'c09117a2931f41998633095f8228829d', '2022-03-30 15:45:57.209', 'c09117a2931f41998633095f8228829d', '2022-03-30 15:45:57.209');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('bb1a674789f24ba3aa0052d37f1f28f8', '获取当前用户信息', '/auth/v1/current-user', 1, '获取当前用户信息', 331, 1, 3, 3, '5df3b0070ea94620879ef9e95f161a7f', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:53:21.592', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:53:21.592');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('5df3b0070ea94620879ef9e95f161a7f', '权限认证', '', 2, '认证，权限管理', 300, 2, 1, 1, 'STS_TREE_TOP_ID', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:10:21.913', 'c09117a2931f41998633095f8228829d', '2022-02-16 16:10:21.913');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('60e13997c9c3496098c8668e878153b6', '图片验证码', '/code/v1/imageCode', 1, '获取登录图片验证码', 311, 2, 3, 3, '5df3b0070ea94620879ef9e95f161a7f', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:49:09.536', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:49:09.536');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('cdea7f51ddc14fb1b1f71a40dbd16e3e', '刷新token', '/auth/v1/token-refresh', 1, '刷新token', 341, 2, 3, 3, '5df3b0070ea94620879ef9e95f161a7f', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:51:00.781', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:51:00.781');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('7e2439a81eb04c5da22d1c825bee7076', '登录接口', '/**/login', 1, '登录地址', 321, 2, 3, 2, '5df3b0070ea94620879ef9e95f161a7f', 'c09117a2931f41998633095f8228829d', '2022-02-10 17:19:51.476', 'c09117a2931f41998633095f8228829d', '2022-02-10 17:19:51.476');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('9b2e4be37fab41e5a49098de363750eb', '文件未完成列表', '/fileMgr/v1/getUnCompleteFileList', 1, '获取未上传完成的文件', 222, 2, 3, 3, '246fabe3cc0645248fbfe7e130ea44ce', 'c09117a2931f41998633095f8228829d', '2022-03-30 15:45:57.209', 'c09117a2931f41998633095f8228829d', '2022-03-30 15:45:57.209');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('f43e063845824fcba3bc20d12aeaa68c', '新增菜单', '/menuMgr/v1/saveMenu', 1, '新增菜单', 122, 2, 3, 3, '0408d6b35bd64bfab1df79e39d4ad9a0', 'c09117a2931f41998633095f8228829d', '2022-02-07 19:56:16.333', 'c09117a2931f41998633095f8228829d', '2022-02-14 10:03:19.109');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('06340d090bac4116a7b90096c77c69ea', '文件目录管理', '/catalogMgr/v1/**', 2, '文件目录管理', 210, 2, 2, 2, '561382a2f4e4486aae42a51016d68a92', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('be4811cec78a406eb5b8ad4c4e5698bb', '文件预览', '/transfer/v1/preview/**', 1, '文件预览', 243, 2, 3, 3, 'fa4357a946024eb39d7556d9d5f8cc8a', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:21:02.051', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:21:02.051');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('d8dcf47996a14878b6a5e4ac34648754', '新增用户', '/userMgr/v1/saveUser', 1, '新增用户', 112, 2, 3, 3, 'b7984ae1480142638b35400ff79d49a0', 'c09117a2931f41998633095f8228829d', '2022-02-10 16:32:14.467', 'c09117a2931f41998633095f8228829d', '2022-02-10 16:32:14.467');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('596b863ba2274be4b16e514e0d861dbc', '监控系统', '', 2, '监控系统', 300, 2, 1, 1, 'STS_TREE_TOP_ID', 'c09117a2931f41998633095f8228829d', '2022-03-25 15:10:09.061', 'c09117a2931f41998633095f8228829d', '2022-02-17 22:17:47.804');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('e3245778226740bf9a99f09d4f9410d6', 'druid', '/druid/**', 1, 'druid数据库监控', 310, 2, 2, 2, '596b863ba2274be4b16e514e0d861dbc', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639', 'c09117a2931f41998633095f8228829d', '2022-03-17 16:33:49.639');
INSERT INTO "public"."smk_tbl_sys_menu" VALUES ('326a02fd93f24990bef226f4d1d79b9d', '获取文件列表', '/catalogMgr/v1/getCatalogList', 1, '获取文件和文件夹列表', 211, 2, 3, 3, '06340d090bac4116a7b90096c77c69ea', 'c09117a2931f41998633095f8228829d', '2022-03-18 10:28:01.755', 'c09117a2931f41998633095f8228829d', '2022-03-18 10:28:01.755');

-- ----------------------------
-- Table structure for smk_tbl_sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_sys_role";
CREATE TABLE "public"."smk_tbl_sys_role" (
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role_name" varchar(128) COLLATE "pg_catalog"."default",
  "role_desc" varchar(255) COLLATE "pg_catalog"."default",
  "is_secret" int2,
  "is_need_authorized" int2,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "create_user" varchar(255) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "role_status" int2 NOT NULL DEFAULT 1
)
;
COMMENT ON COLUMN "public"."smk_tbl_sys_role"."is_secret" IS '1：是；2：否';
COMMENT ON COLUMN "public"."smk_tbl_sys_role"."is_need_authorized" IS '1：是；2：否';

-- ----------------------------
-- Records of smk_tbl_sys_role
-- ----------------------------
INSERT INTO "public"."smk_tbl_sys_role" VALUES ('5', 'druid', 'druid监控页面', 1, 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO "public"."smk_tbl_sys_role" VALUES ('2', '系统白名单', '系统白名单', 1, 2, NULL, NULL, NULL, NULL, 1);
INSERT INTO "public"."smk_tbl_sys_role" VALUES ('1', '超级管理员', '系统管理权限', 1, 1, NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for smk_tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_sys_user";
CREATE TABLE "public"."smk_tbl_sys_user" (
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(128) COLLATE "pg_catalog"."default",
  "user_pwd" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "nick_name" varchar(128) COLLATE "pg_catalog"."default",
  "phone_num" varchar(16) COLLATE "pg_catalog"."default",
  "email_address" varchar(32) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "update_user" varchar(64) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of smk_tbl_sys_user
-- ----------------------------
INSERT INTO "public"."smk_tbl_sys_user" VALUES ('c09117a2931f41998633095f8228829d', 'admin', 'ef5477d3b95cd06c5cf3d52297c4c5112c2569b4e2d93be2c7cc5f313d303438efb12cf0ea1ed2b627f5ea18d4a0eafbcb6abb3c572d009aeac727555ca7219cf5318296b2e8014a010b5d6d6e557193735a493001f2a7a618ac22dc0faecae2554985749cf3d932b579c04e9b1c2e4e93235c1efb6522a8ea89c58818', '2022-02-07 20:17:27', '2022-02-07 20:17:30', '码客-大白菜', '13259720691', 'sanmako@163.com', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '7104bc7451c64ddf8e5b1e24f3fdc02c');
INSERT INTO "public"."smk_tbl_sys_user" VALUES ('0521687819c242818d41c901e69c0cc1', 'test', 'e81ac1329302e865ae37503e0372d0f9f0eb0939a170317b42e752b237228f510df7cd5801017a543e2208f4b7e2eb55cbb5c2a757f5b49bc8350011df0a08279c39475f355b55f5158e855f0eb2cc97e3b801fdad38d7d48c1c966d227af477a0a9720faafca66aa7c3abe5b8956d9250038f014a50f1fb0f81b47aab', '2022-04-08 18:57:23.714', '2022-04-08 18:57:23.714', '码客-空心菜', '13259720691', '11231004@bjtu.edu.cn', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '19e6855b8f72404e93f5ff1fbf645776');
INSERT INTO "public"."smk_tbl_sys_user" VALUES ('9ca29aa5c4cb4248b1fe95b776e89b71', 'druid', 'e81ac1329302e865ae37503e0372d0f9f0eb0939a170317b42e752b237228f510df7cd5801017a543e2208f4b7e2eb55cbb5c2a757f5b49bc8350011df0a08279c39475f355b55f5158e855f0eb2cc97e3b801fdad38d7d48c1c966d227af477a0a9720faafca66aa7c3abe5b8956d9250038f014a50f1fb0f81b47aab', '2022-04-08 19:37:28.775', '2022-04-08 19:37:28.775', 'Druid', '13259720691', '11231004@bjtu.edu.cn', 'c09117a2931f41998633095f8228829d', 'c09117a2931f41998633095f8228829d', '08e310fa4d514d158be040fe1980ba44');

-- ----------------------------
-- Table structure for smk_tbl_test
-- ----------------------------
DROP TABLE IF EXISTS "public"."smk_tbl_test";
CREATE TABLE "public"."smk_tbl_test" (
  "test_id" int4 NOT NULL,
  "test_name" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."smk_tbl_test" IS '测试表';

-- ----------------------------
-- Records of smk_tbl_test
-- ----------------------------
INSERT INTO "public"."smk_tbl_test" VALUES (1, 'sanmako');

-- ----------------------------
-- Primary Key structure for table smk_tbl_file
-- ----------------------------
ALTER TABLE "public"."smk_tbl_file" ADD CONSTRAINT "smk_tbl_file_pkey" PRIMARY KEY ("file_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_file_catalog
-- ----------------------------
ALTER TABLE "public"."smk_tbl_file_catalog" ADD CONSTRAINT "smk_tbl_file_catalog_pkey" PRIMARY KEY ("catalog_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_file_slice
-- ----------------------------
ALTER TABLE "public"."smk_tbl_file_slice" ADD CONSTRAINT "smk_tbl_file_status_pkey" PRIMARY KEY ("slice_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_rela_role_menu
-- ----------------------------
ALTER TABLE "public"."smk_tbl_rela_role_menu" ADD CONSTRAINT "smk_tbl_rela_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_rela_role_user
-- ----------------------------
ALTER TABLE "public"."smk_tbl_rela_role_user" ADD CONSTRAINT "smk_tbl_rela_role_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_storage
-- ----------------------------
ALTER TABLE "public"."smk_tbl_storage" ADD CONSTRAINT "smk_tbl_storage_pkey" PRIMARY KEY ("storage_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_sys_role
-- ----------------------------
ALTER TABLE "public"."smk_tbl_sys_role" ADD CONSTRAINT "smk_tbl_sys_role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_sys_user
-- ----------------------------
ALTER TABLE "public"."smk_tbl_sys_user" ADD CONSTRAINT "smk_tbl_sys_user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table smk_tbl_test
-- ----------------------------
ALTER TABLE "public"."smk_tbl_test" ADD CONSTRAINT "smk_tbl_test_pkey" PRIMARY KEY ("test_id");
