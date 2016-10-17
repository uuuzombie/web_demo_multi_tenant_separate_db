
create table an_log (
	`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键自增',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	`user_id` int NOT NULL COMMENT '用户ID',
	`role_id` int NOT NULL COMMENT '角色ID',
	`server_ip` varchar(64) NOT NULL COMMENT '服务器IP',
	`client_ip` varchar(64) NOT NULL COMMENT '客户端IP',
	`action_type` int NOT NULL COMMENT '动作类型',
	`feature_type` int NOT NULL COMMENT '模块类型',
	`action_info` varchar(255) NOT NULL COMMENT '操作内容'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '审计日志表';


ALTER TABLE an_log ADD INDEX idx_an_log_create_time(create_time);
ALTER TABLE an_log ADD INDEX idx_an_log_user_id(user_id);


insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 10:00', 1, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 12:00', 2, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 13:00', 3, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 14:00', 4, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
