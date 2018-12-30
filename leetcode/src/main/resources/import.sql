-- MariaDB --
drop table if exists rbac_user;

create table rbac_user (id int primary key auto_increment, name varchar(30), account DECIMAL(10, 2), create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP, version int DEFAULT 1);

insert into rbac_user (name, account) values ('admin1', 1000);

-- https://leetcode-cn.com/problems/delete-duplicate-emails/
drop table if exists logs;

create table logs (Id int primary key auto_increment, Num int);

insert into logs (Num) values (1);
insert into logs (Num) values (1);
insert into logs (Num) values (1);
insert into logs (Num) values (2);
insert into logs (Num) values (1);
insert into logs (Num) values (2);
insert into logs (Num) values (2);

-- https://leetcode-cn.com/problems/employees-earning-more-than-their-managers/
drop table if exists employee;

create table employee (Id int primary key auto_increment, name varchar(20), salary int, manager_id int);

insert into employee (name, salary, manager_id) values ("Joe", 70000, 3);
insert into employee (name, salary, manager_id) values ("Henry", 80000, 4);
insert into employee (name, salary, manager_id) values ("Sam", 60000, NULL);
insert into employee (name, salary, manager_id) values ("Max", 90000, NULL);

drop table if exists employee1;

create table employee1 (Id int primary key auto_increment, name varchar(20), salary int, manager_id int);

insert into employee1 (name, salary, manager_id) values ("Mark", 4000, 3);
insert into employee1 (name, salary, manager_id) values ("Alan", 2000, NULL);
insert into employee1 (name, salary, manager_id) values ("Jack", 3000, 2);

-- https://leetcode-cn.com/problems/rising-temperature/
drop table if exists weather;

create table weather (Id int primary key auto_increment, date date, temperature int);
insert into weather (date, temperature) values (DATE_FORMAT("2015-1-1","%Y-%m-%d"), 13);
insert into weather (date, temperature) values (DATE_FORMAT("2015-1-2","%Y-%m-%d"), 23);
insert into weather (date, temperature) values (DATE_FORMAT("2015-1-3","%Y-%m-%d"), 33);
insert into weather (date, temperature) values (DATE_FORMAT("2015-1-4","%Y-%m-%d"), 43);

-- https://leetcode-cn.com/problems/swap-salary/
drop table if exists salary;

create table salary (Id int primary key auto_increment, name varchar(4), sex varchar(5), salary int);
insert into salary (name, sex, salary) values ("A", "m", 2500);
insert into salary (name, sex, salary) values ("B", "f", 1500);
insert into salary (name, sex, salary) values ("C", "m", 5500);
insert into salary (name, sex, salary) values ("D", "f", 500);

-- https://leetcode-cn.com/problems/department-highest-salary/
drop table if exists employee_department;

create table employee_department (Id int primary key auto_increment, name varchar(20), salary int, department_id int);

insert into employee_department (name, salary, department_id) values ("Joe", 70000, 1);
insert into employee_department (name, salary, department_id) values ("Henry", 80000, 2);
insert into employee_department (name, salary, department_id) values ("Sam", 60000, 2);
insert into employee_department (name, salary, department_id) values ("Max", 90000, 1);
insert into employee_department (name, salary, department_id) values ("Jane", 69000, 1);
insert into employee_department (name, salary, department_id) values ("Rand", 85000, 1);

drop table if exists department;

create table department (Id int primary key auto_increment, name varchar(10));

insert into department (name) values ("IT");
insert into department (name) values ("Sales");


drop table if exists employee_department1;

create table employee_department1 (Id int primary key auto_increment, name varchar(20), salary int, department_id int);
insert into employee_department1 (name, salary, department_id) values ("Joe", 60000, 1);
insert into employee_department1 (name, salary, department_id) values ("Sam", 50000, 1);
insert into employee_department1 (name, salary, department_id) values ("Henry", 80000, 2);
insert into employee_department1 (name, salary, department_id) values ("Max", 50000, 2);
insert into employee_department1 (name, salary, department_id) values ("Leon", 70000, 2);
-- delete from employee_department1 where id = 3;

drop table if exists department1;

create table department1 (Id int primary key auto_increment, name varchar(10));

insert into department1 (name) values ("IT");
insert into department1 (name) values ("HR");