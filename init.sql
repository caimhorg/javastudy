create table system_menu
(
    id        varchar(255) not null comment 'id主键'
        primary key,
    menu_name varchar(255) null comment '菜单名',
    url_      varchar(255) null comment '菜单路径',
    parent_id varchar(255) null comment '父id',
    level_    varchar(255) null comment '菜单等级'
)
    comment '菜单表';

INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('1', '技术书籍', 'books/index.do', '0', '1');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('2', '技术文章', 'article/index.do', '0', '1');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('21', '用户管理', 'user/index.do', '4', '2');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('22', '角色管理', 'role/index.do', '4', '2');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('23', '权限管理', 'role/authIndex.do', '4', '2');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('3', '类别管理', 'category/index.do', '0', '2');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('4', '用户权限', '', '0', '1');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('5', '借阅审批', 'approve/index.do', '0', '1');
INSERT INTO javastudy.system_menu (id, menu_name, url_, parent_id, level_) VALUES ('6', '开发日志', 'devlog/index.do', '0', '1');
create table system_role
(
    id           varchar(255) not null comment 'id主键'
        primary key,
    role_name    varchar(255) null comment '角色名',
    creator_id   varchar(255) null comment '创建者id',
    creator_name varchar(255) null comment '创建者名',
    create_time  varchar(255) null comment '创建时间'
)
    comment '角色表';

INSERT INTO javastudy.system_role (id, role_name, creator_id, creator_name, create_time) VALUES ('1', '超级管理员', '--', '--', '--');
INSERT INTO javastudy.system_role (id, role_name, creator_id, creator_name, create_time) VALUES ('2', '普通角色', '--', '--', '--');
create table system_role_menu
(
    id      varchar(255) null comment 'id',
    role_id varchar(255) null comment '角色id',
    menu_id varchar(255) null comment '菜单id'
)
    comment '角色菜单关联表';

INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('1', '1', '1');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('2', '1', '2');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('3', '1', '3');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('4', '1', '4');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('5', '1', '5');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('6', '1', '6');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('7', '1', '21');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('8', '1', '22');
INSERT INTO javastudy.system_role_menu (id, role_id, menu_id) VALUES ('9', '1', '23');
create table system_user
(
    id          varchar(255) not null comment 'id'
        primary key,
    user_name   varchar(255) null comment '用户名',
    password_   varchar(255) null comment '密码',
    phone_      varchar(255) null comment '手机',
    email_      varchar(255) null comment '邮箱',
    id_card     varchar(255) null comment '身份证',
    user_status int          null comment '用户状态',
    create_time varchar(255) null comment '创建时间'
)
    comment '用户表';

INSERT INTO javastudy.system_user (id, user_name, password_, phone_, email_, id_card, user_status, create_time) VALUES ('1', 'admin', '96e79218965eb72c92a549dd5a330112', '--', '--', '--', 0, '--');
create table system_user_role
(
    id      varchar(255) not null comment 'id'
        primary key,
    user_id varchar(255) null comment '用户名',
    role_id varchar(255) null comment '角色id'
)
    comment '用户角色表';

INSERT INTO javastudy.system_user_role (id, user_id, role_id) VALUES ('1', '1', '1');