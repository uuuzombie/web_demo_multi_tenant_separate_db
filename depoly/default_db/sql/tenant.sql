
create table tenant(
	id serial not null primary key,
	name varchar(255) not null unique,
	client_id varchar(32) not null unique,
	device_id varchar(255) not null unique,
	device_token varchar(255) not null unique,
	db_name varchar(255) not null unique,
	create_time timestamp not null,
	status smallint not null default 1
);



--表说明  
COMMENT ON TABLE tenant IS '租戶信息表';
--字段说明 
COMMENT ON COLUMN tenant.id IS '主键自增';
COMMENT ON COLUMN tenant.name IS '租戶名称';
COMMENT ON COLUMN tenant.client_id IS '租戶客户域名';
COMMENT ON COLUMN tenant.device_id IS '租户设备ID';
COMMENT ON COLUMN tenant.device_token IS '租戶设备token';
COMMENT ON COLUMN tenant.db_name IS '数据库名';
COMMENT ON COLUMN tenant.create_time IS '创建时间';
COMMENT ON COLUMN tenant.status IS '状态 1-正常 2-删除';


create index idx_tenant_name on tenant(name);
create index idx_tenant_client_id on tenant(client_id);
create index idx_tenant_device_id on tenant(device_id);

insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (1, 'tenant1.com', 'tenant1', '4a3e7145-e757-4660-b3ea-8328e88d8f66', '1aaa57afec464a979f946f1c6967a8cf', 'tenant1_db', '2016-09-10 10:10:00');
insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (2, 'tenant2.com', 'tenant2', '1be5de0a-165e-4ac2-96ba-a392c20ad36d', '2bbb57afec464a979f946f1c6967a8cf', 'tenant2_db', '2016-09-10 10:10:00');
insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (3, 'tenant3.com', 'tenant3', '18fa67b6-35e4-4abf-aa46-bddd0e08765b', '3ccc57afec464a979f946f1c6967a8cf', 'tenant3_db', '2016-09-10 10:10:00');


select setval('tenant_id_seq', (select max(id) from tenant));

