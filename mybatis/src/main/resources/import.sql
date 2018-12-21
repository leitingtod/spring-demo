-- MariaDB --
drop table if exists rbac_user;

create table rbac_user (id int primary key auto_increment, name varchar(30), account DECIMAL(10, 2), create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP, version int DEFAULT 1);

insert into rbac_user (name, account) values ('admin1', 1000);
