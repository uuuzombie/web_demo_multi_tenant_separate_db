drop table if exists `an_log`;

create table `an_log` (
  `id` int unsigned not null auto_increment comment 'id',
  `create_time` timestamp not null comment 'create time'
)engine=InnoDB DEFAULT CHARSET=UTF8;

insert into an_log(id, create_time) values (1, '2016-09-10 00:10:00');
insert into an_log(id, create_time) values (2, '2016-09-10 00:20:00');
insert into an_log(id, create_time) values (3, '2016-09-10 00:30:00');
insert into an_log(id, create_time) values (4, '2016-09-10 00:40:00');
insert into an_log(id, create_time) values (5, '2016-09-10 00:50:00');
