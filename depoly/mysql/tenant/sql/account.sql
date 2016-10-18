
create table account (
	`id` serial NOT NULL AUTO_INCREMENT COMMENT '主键自增',
	`user_name` varchar(255) NOT NULL COMMENT '用户名',
	`password` varchar(255) NOT NULL COMMENT '密码'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';



ALTER TABLE account ADD UNIQUE INDEX unique_idx_account_user_name(user_name);


#tenant1
insert into account (user_name, password) values ('admin@tenant1.com', '123');
insert into account (user_name, password) values ('guest@tenant1.com', '123');

-- --tenant2
-- insert into account (user_name, password) values ('admin@tenant2.com', '123');
-- insert into account (user_name, password) values ('guest@tenant2.com', '123');

-- --tenant3
-- insert into account (user_name, password) values ('admin@tenant3.com', '123');
-- insert into account (user_name, password) values ('guest@tenant3.com', '123');


