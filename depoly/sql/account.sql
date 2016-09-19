
create table account (
	id serial not null primary key,
	user_name varchar(255) not null,
	password varchar(255) not null
);


--表说明  
COMMENT ON TABLE account IS '用户表';  
--字段说明 
COMMENT ON COLUMN account.id IS '主键自增';
COMMENT ON COLUMN account.user_name IS '用户名';
COMMENT ON COLUMN account.password IS '密码';


create index idx_account_user_name on account(user_name);


-- --tenant1
-- insert into account (user_name, password) values ('admin@tenant1.com', '123');
-- insert into account (user_name, password) values ('guest@tenant1.com', '123');

-- --tenant2
-- insert into account (user_name, password) values ('admin@tenant2.com', '123');
-- insert into account (user_name, password) values ('guest@tenant2.com', '123');

--tenant3
insert into account (user_name, password) values ('admin@tenant3.com', '123');
insert into account (user_name, password) values ('guest@tenant3.com', '123');


