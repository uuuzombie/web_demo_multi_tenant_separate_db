
create user sps with password 'sps';

create database default_db owner sps;
grant all privileges on database default_db to sps;