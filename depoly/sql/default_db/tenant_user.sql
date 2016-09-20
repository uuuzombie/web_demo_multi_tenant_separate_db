
create table tenant_user(
	id serial not null primary key,
	tenant_id int not null,
	user_name varchar(255) not null,
	create_time timestamp not null,
	status smallint not null default 1
);



--表说明  
COMMENT ON TABLE tenant_user IS '租戶用户表';  
--字段说明 
COMMENT ON COLUMN tenant_user.id IS '主键自增';
COMMENT ON COLUMN tenant_user.tenant_id IS '租户ID';
COMMENT ON COLUMN tenant_user.user_name IS '用戶名称';
COMMENT ON COLUMN tenant_user.create_time IS '创建时间';
COMMENT ON COLUMN tenant_user.status IS '状态 1-正常 2-删除';

create index idx_tenant_user_tenant_id on tenant_user(tenant_id);
create unique index unique_idx_tenant_user_user_name on tenant_user(user_name);



insert into tenant_user(tenant_id, user_name, create_time) values (1, 'admin@tenant1.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (1, 'guest@tenant1.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (2, 'admin@tenant2.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (2, 'guest@tenant2.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (3, 'admin@tenant3.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (3, 'guest@tenant3.com', '2016-09-10 10:10:00');

