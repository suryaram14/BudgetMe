# CS151-Project
Expense Management System

To run homepage click on Main.java in the application package. From here the functionality that we added are adding transactions + deleting transactions. To add fill in details of date, description, and amount and click on "Add" button. To delete a transaction click on the row of the transaction and press delete.



Another added functionality is the login page where we log in and if successful goes to to the homepage.



To run, database must be initialized in mysql and eclipse project must reference the mysql connector jar.




Database details are provided below






    create database Account_database;

    use Account_database;

    create table Account(
    transactionID int PRIMARY KEY auto_increment, 
    date date,
    description varchar(30),
    amount float(10)
    );

    SET SQL_SAFE_UPDATES = 0;

    create table users(
    user_id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(30),
    password varchar(30)
    );

    insert into users(username, password) values('test','test');



*NOTE: Application will run as long as MySQL server is up and running. If server is not running, the application will not be able to read and change data accordingly






DEMO



![ezgif com-gif-maker](https://user-images.githubusercontent.com/60376265/98766098-d4114b80-2393-11eb-96a2-ea548f2ac105.gif)



