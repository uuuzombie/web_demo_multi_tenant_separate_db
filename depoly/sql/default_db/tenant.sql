
create table tenant(
	id serial not null primary key,
	name varchar(255) not null unique,
	db_name varchar(255) not null unique,
	create_time timestamp not null,
	status smallint not null default 1
);



--表说明  
COMMENT ON TABLE tenant IS '租戶信息表';  
--字段说明 
COMMENT ON COLUMN tenant.id IS '主键自增';
COMMENT ON COLUMN tenant.name IS '租戶名称';
COMMENT ON COLUMN tenant.db_name IS '数据库名';
COMMENT ON COLUMN tenant.create_time IS '创建时间';
COMMENT ON COLUMN tenant.status IS '状态 1-正常 2-删除';



insert into tenant (id, name, db_name, create_time) values (1, 'tenant1.com', 'tenant1', '2016-09-10 10:10:00');
insert into tenant (id, name, db_name, create_time) values (2, 'tenant2.com', 'tenant2', '2016-09-10 10:10:00');
insert into tenant (id, name, db_name, create_time) values (3, 'tenant3.com', 'tenant3', '2016-09-10 10:10:00');


select setval('tenant_id_seq', (select max(id) from tenant));

