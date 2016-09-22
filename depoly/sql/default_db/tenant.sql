
create table tenant(
	id serial not null primary key,
	name varchar(255) not null unique,
	token varchar(255) not null unique,
	db_name varchar(255) not null unique,
	create_time timestamp not null,
	status smallint not null default 1
);



--表说明  
COMMENT ON TABLE tenant IS '租戶信息表';  
--字段说明 
COMMENT ON COLUMN tenant.id IS '主键自增';
COMMENT ON COLUMN tenant.name IS '租戶名称';
COMMENT ON COLUMN tenant.token IS '租戶token';
COMMENT ON COLUMN tenant.db_name IS '数据库名';
COMMENT ON COLUMN tenant.create_time IS '创建时间';
COMMENT ON COLUMN tenant.status IS '状态 1-正常 2-删除';


create index idx_tenant_name on tenant(name);
create index idx_tenant_token on tenant(token);

insert into tenant (id, name, token, db_name, create_time) values (1, 'tenant1.com', '4a3e7145-e757-4660-b3ea-8328e88d8f66', 'tenant1', '2016-09-10 10:10:00');
insert into tenant (id, name, token, db_name, create_time) values (2, 'tenant2.com', '1be5de0a-165e-4ac2-96ba-a392c20ad36d', 'tenant2', '2016-09-10 10:10:00');
insert into tenant (id, name, token, db_name, create_time) values (3, 'tenant3.com', '18fa67b6-35e4-4abf-aa46-bddd0e08765b', 'tenant3', '2016-09-10 10:10:00');


select setval('tenant_id_seq', (select max(id) from tenant));

