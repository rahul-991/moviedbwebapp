<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connection Pooling Report</title>
</head>
<body>
<h1>Connection Pooling Report</h1>

<p> Connection pooling within a particular web application, in this case for 'fabflix' context, is implemented by creating 2 RESOURCES in
context.xml file, which is added to the META-INF folder of the application.

The content of the context.xml file is:

Context path="/fabflix"
Resource name="read"  auth="Container" factory="org.apache.commons.dbcp.BasicDataSourceFactory" driverClassName="com.mysql.jdbc.Driver" maxActive="100" maxIdle="100" password="rasmysql" type="javax.sql.DataSource" url="jdbc:mysql://localhost:3306/moviedb" username="root"
Resource name="write"  auth="Container" factory="org.apache.commons.dbcp.BasicDataSourceFactory" driverClassName="com.mysql.jdbc.Driver" maxActive="100" maxIdle="100" password="rasmysql" type="javax.sql.DataSource" url="jdbc:mysql://172.31.0.51:3306/moviedb" username="root"

In this case there will be a maximum of 10,000 connection objects active at a particular time, for either a 'read' or a 'write' resource.

Once a servlet needs to use a connection object, it checks the pool for free connection objects. If total connection objects in the pool<max active 
a new connection object is created and upon completion of the servlet operation, it is returned to the pool. If total objects in the pool>max_active
then the servlet has to wait until a connection object is available.
</p>
<br>
<p> In the case of the case of 2 backend servers, we have a pair of master-slave servers. Any update to the database through the 'fabflix'
web application on either of the 2 servers, causes a connection object to be formed with the 'write' resource that creates a connection to 
the master DB and updates the database on the master. This is what happens to requests that go to the slave server which update contents of 
the database.

Any updates to the database through the master server creates a connection object in its own server and puts in the pool. All database read 
requests use connection objects of the server in which they are requested. The connection object creation and return of the pool follows the 
same rules as of a single instance.
</p>
</body>
</html>