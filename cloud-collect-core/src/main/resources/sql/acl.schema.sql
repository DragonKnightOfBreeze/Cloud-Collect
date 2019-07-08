-- 创建acl（访问控制列表）所需要的数据库表

create table `acl_class` (
  `id` bigint(20) not null auto_increment,
  `class` varchar(100) not null,
  primary key (`id`),
  unique key `unique_uk_2` (`class`)
);

create table `acl_entry` (
  `id` bigint(20) not null auto_increment,
  `acl_object_identity` bigint(20) not null,
  `ace_order` int(11) not null,
  `sid` bigint(20) not null,
  `mask` int(11) not null,
  `granting` tinyint(1) not null,
  `audit_success` tinyint(1) not null,
  `audit_failure` tinyint(1) not null,
  primary key (`id`),
  unique key `unique_uk_4` (`acl_object_identity`, `ace_order`),
  key `sid` (`sid`)
);

create table `acl_object_identity` (
  `id` bigint(20) not null auto_increment,
  `object_id_class` bigint(20) not null,
  `object_id_identity` bigint(20) not null,
  `parent_object` bigint(20) default null,
  `owner_sid` bigint(20) default null,
  `entries_inheriting` tinyint(1) not null,
  primary key (`id`),
  unique key `unique_uk_3` (`object_id_class`, `object_id_identity`),
  key `owner_sid` (`owner_sid`),
  key `parent_object` (`parent_object`)
);

create table `acl_sid` (
  `id` bigint(20) not null auto_increment,
  `principal` tinyint(1) not null,
  `sid` varchar(100) not null,
  primary key (`id`),
  unique key `unique_uk_1` (`principal`, `sid`)
);
