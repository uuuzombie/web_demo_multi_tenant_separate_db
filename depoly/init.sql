
drop user if exists sps;
create user sps with password 'sps';

drop database if exists default_db;
create database default_db owner sps;
grant all privileges on database default_db to sps;




