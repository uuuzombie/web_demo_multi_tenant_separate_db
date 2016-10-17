
create table role (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `role_name` varchar(255) NOT NULL COMMENT '角色名'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色表';


insert into role (role_name) values ('super admin');
insert into role (role_name) values ('admin');
insert into role (role_name) values ('user');
insert into role (role_name) values ('audit');
