create database Account_database;


create table Account(
transactionID int PRIMARY KEY auto_increment, 
username varchar(30),
date date,
description varchar(30),
category varchar(30),
amount double
);


create table users(
user_id int PRIMARY KEY AUTO_INCREMENT,
username varchar(30),
password varchar(30),
UNIQUE KEY unique_username (username)
);
