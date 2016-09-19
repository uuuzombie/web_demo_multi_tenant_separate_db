
create table role (
    id serial not null primary key,
    role_name varchar(255) not null
);


--表说明
COMMENT ON TABLE role IS '角色表';
--字段说明
COMMENT ON COLUMN role.id IS '主键自增';
COMMENT ON COLUMN role.role_name IS '角色名';


insert into role (role_name) values ('super admin');
insert into role (role_name) values ('admin');
insert into role (role_name) values ('user');
insert into role (role_name) values ('audit');
