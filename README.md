# CS 304 Project

## MySQL Authentication
- DB name: airport
- User: cs304
- Password: cs304

## Setup
1. Download MySQL. Set up a user called ```cs304``` with password ```cs304```.
2. Setup the airport database.
- Inside the airport.sql, replace the path to the airport.sql to your own path
- mysql -u cs304 -p < airport.sql
3. Download JDBC connector: https://dev.mysql.com/downloads/connector/j/
2. Add the mySQL connector library to your IDE build path
3. ```File``` -> ```Project Structure```. Mark every **top level** directory as ```sources``` except the out directory.
4. If you haven't already, create a directory inside the repo called ```out```
5. Go to ```File``` -> ```Project Structure``` -> ```Project Compiler Output```.
Inside that, enter something like ```C:<some-path>\cs304-project\out```
6. Then go to the ```Modules``` > ```Paths``` and make sure you are inheriting the project compile path


## TODOs

a) Create the database schema (tables and other database objects). Each member will work on specific tables. 
Stanley, Rachel, Alex, CK

b) Create data for the tables and populate them. Load the data with SQL INSERT statements. Create a scripting program to generate random data.
Alex, Stanley

c) Code each set of queries and test them in mySQL
Rachel, CK

d) Code the frontend gui using HTML, Bootstrap and Javascript to handle the SQL and format the input appropriately to send to the backend.
Alex, CK

e) Code the Java Backend and connect the program to mySQL. Take in the input from the frontend and embed the SQL input from the frontend. 
      -     Stanley, Alex, CK, Rachel

f) Test each set of queries and make sure the specified constraints in the database schema are also tested. 
      -     Alex, Rachel

g) Document every part of the project.
      -     Stanley, Alex, CK, Rachel

