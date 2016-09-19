
create table an_log (
	id bigserial primary key not null,
	create_time timestamp not null,
	user_id int not null,
	role_id int not null,
	server_ip varchar(64) not null,
	client_ip varchar(64) not null,
	action_type int not null,
	feature_type int not null,
	action_info varchar(255) not null
);

--表说明  
COMMENT ON TABLE an_log IS '审计日志表';  
--字段说明 
COMMENT ON COLUMN an_log.id IS '主键自增';
COMMENT ON COLUMN an_log.create_time IS '创建时间';
COMMENT ON COLUMN an_log.user_id IS '用户ID';
COMMENT ON COLUMN an_log.role_id IS '角色ID';
COMMENT ON COLUMN an_log.server_ip IS '服务器IP';
COMMENT ON COLUMN an_log.client_ip IS '客户端IP';
COMMENT ON COLUMN an_log.action_type IS '动作类型';
COMMENT ON COLUMN an_log.feature_type IS '模块类型';
COMMENT ON COLUMN an_log.action_info IS '操作内容';

create index idx_an_log_create_time on an_log(create_time);
create index idx_an_log_user_id on an_log(user_id);


insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 10:00', 1, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 12:00', 2, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 13:00', 3, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
insert into an_log(create_time, user_id, role_id, server_ip, client_ip, action_type, feature_type, action_info) values ('2016-10-01 14:00', 4, 1, '172.20.100.1', '127.0.0.1', 1, 1, '[]');
