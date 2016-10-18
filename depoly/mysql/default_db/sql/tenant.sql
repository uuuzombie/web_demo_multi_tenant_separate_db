
create table tenant(
	`id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
	`name` varchar(255) NOT NULL UNIQUE COMMENT '租戶名称',
	`client_id` varchar(32) NOT NULL UNIQUE COMMENT '租戶客户域名',
	`device_id` varchar(255) NOT NULL UNIQUE COMMENT '租户设备ID',
	`device_token` varchar(255) NOT NULL UNIQUE COMMENT '租戶设备token',
	`db_name` varchar(255) NOT NULL UNIQUE COMMENT '数据库名',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	`status` smallint NOT NULL DEFAULT 1 COMMENT '状态 1-正常 2-删除'
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT '租戶信息表';



ALTER TABLE tenant ADD INDEX idx_tenant_name(name);
ALTER TABLE tenant ADD INDEX idx_tenant_client_id(client_id);
ALTER TABLE tenant ADD INDEX idx_tenant_device_id(device_id);

insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (1, 'tenant1.com', 'tenant1', '4a3e7145-e757-4660-b3ea-8328e88d8f66', '1aaa57afec464a979f946f1c6967a8cf', 'tenant1_db', '2016-09-10 10:10:00');
insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (2, 'tenant2.com', 'tenant2', '1be5de0a-165e-4ac2-96ba-a392c20ad36d', '2bbb57afec464a979f946f1c6967a8cf', 'tenant2_db', '2016-09-10 10:10:00');
insert into tenant (id, name, client_id, device_id, device_token, db_name, create_time) values (3, 'tenant3.com', 'tenant3', '18fa67b6-35e4-4abf-aa46-bddd0e08765b', '3ccc57afec464a979f946f1c6967a8cf', 'tenant3_db', '2016-09-10 10:10:00');



