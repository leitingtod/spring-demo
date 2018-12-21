-- MariaDB --
drop table if exists rbac_user;

create table rbac_user (id int primary key auto_increment, name varchar(30), password varchar(30), email varchar(30), account DECIMAL(10, 2), create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP);

insert into rbac_user (name, password, email, account) values ('admin1', '1231', 'admin@rbac.com', 1000);
