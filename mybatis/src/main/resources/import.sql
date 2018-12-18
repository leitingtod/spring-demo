-- MariaDB --
drop table if exists city;

create table city (id int primary key auto_increment, name varchar(30), state varchar(10), country varchar(10));

insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
insert into city (name, state, country) values ('Nanjing', 'JS', 'CN');
insert into city (name, state, country) values ('Beijing', 'BJ', 'CN');
insert into city (name, state, country) values ('Hangzhou', 'ZJ', 'CN');


drop table if exists rbac_user;

create table rbac_user (id int primary key auto_increment, name varchar(30), password varchar(30), email varchar(30), create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP);

insert into rbac_user (name, password, email) values ('admin1', '1231', 'admin@rbac.com');
insert into rbac_user (name, password, email) values ('admin1', '1232', 'admin@rbac.com');
insert into rbac_user (name, password, email) values ('test1', '1233', 'test@rbac.com');
insert into rbac_user (name, password, email) values ('test2', '1234', 'test@rbac.com');
