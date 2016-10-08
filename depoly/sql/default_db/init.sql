
create user if not exists sps with password 'sps';

create database if not exists default_db owner sps;
grant all privileges on database default_db to sps;