## Springboot based Java application for doing CRUD operations on a database using JDBC connection.

### Prerequisities
Java :- version: 11
<br>Spring Boot :- version: 2.3.1 or later
<br>SLF4J :- version: 1.7
<br>Mysql:- version: 5.7
<br>Maven:- version: 3.6 or later

### Installing
1. Clone the project.<br>
2. If maven is setup properly in your system. then you can download all the required dependency.<br>
3. Database is named goal, with table as goals. Schema is:<br>
GoalId: Int<br>
title: Varchar2<br>
details: Varchar2<br>
eta: Int<br>
createDate: Date<br>
updateDate: Date<br>
<br>

### API Documentation.
To test the REST API , use any rest client like postman etc.<br>

<br>PUT: Update a particular Goal, goalId is mandatory.
<br>URL:- http://localhost:8080/goals/101
<br>{
"goalId":"101",<br>
"title":"task4",<br>
"details":" to be",<br>
"eta":"24",<br>
"createDate":"2020-07-07",<br>
"updateDate":"2020-09-07"<br>
}<br>
<br>
GET: If a goalId is passed, then get that goal else return all the goals.
<br>URL:- http://localhost:8080/goals
<br>OR
<br>URL:- http://localhost:8080/goals/101
<br>
<br>POST: Create a new goal with following field: title, details, ETA, createDate, updateDate
<br>URL:- http://localhost:8080/goals
<br>Example:
<br>{
"goalId":"102",<br>
"title":"task2",<br>
"details":"completed",<br>
"eta":"7",<br>
"createDate":"2020-01-02",<br>
"updateDate":"2020-03-02"<br>
}<br>

<br>DELETE: Pass the goalId which needs to be deleted
<br>URL:- http://localhost:8080/goals/102
