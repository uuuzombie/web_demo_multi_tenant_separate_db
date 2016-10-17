
create table tenant_user(
	`id` serial not null primary key COMMENT '主键自增',
	`tenant_id` int not null COMMENT '租户ID',
	`user_name` varchar(255) not null COMMENT '用戶名称',
	`create_time` timestamp not null COMMENT '创建时间',
	`status` smallint not null default 1 COMMENT '状态 1-正常 2-删除'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '租戶用户表';


ALTER TABLE tenant_user ADD INDEX idx_tenant_user_tenant_id(tenant_id);
ALTER TABLE tenant_user ADD UNIQUE INDEX unique_idx_tenant_user_user_name(user_name);



insert into tenant_user(tenant_id, user_name, create_time) values (1, 'admin@tenant1.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (1, 'guest@tenant1.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (2, 'admin@tenant2.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (2, 'guest@tenant2.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (3, 'admin@tenant3.com', '2016-09-10 10:10:00');
insert into tenant_user(tenant_id, user_name, create_time) values (3, 'guest@tenant3.com', '2016-09-10 10:10:00');

