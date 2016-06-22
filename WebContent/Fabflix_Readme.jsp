<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fabflix Readme</title>
</head>
<body>
<h2>Fabflix Readme</h2>
<p><u>Project 5</u></p>
<p>Master  Public IP-->    52.37.9.6              Private IP-->     172.31.0.51<br>
   Slave    Public IP-->    52.33.162.1            Private IP-->     172.31.1.92 </p>
   
<p><b>Program to process logfile of query workload</b><br>
The file Logging.java along with the root of the war file parses the catalina.out file to calculate the average TS and TJ values. <br>
Logging.java accepts the file named "catalina.out" when placed in the same folder along with it.</p>

<p>Follow the commands to compile and execute Logging.java</p><br>

<p> javac -cp . Logging.java </p>
<p> java -cp . Logging </p> <br>

**************************************************************************************************************************************************************************


<p><u>Project 4</u></p>
<p>The Ajax AutoSearch is implemented in the mainpage the link for which is</p>
<a href='<%=request.getContextPath()%>/MainPage.jsp'><%=request.getContextPath()%>/MainPage.jsp</a>
<p>Ajax Popup implemented in the movielist page when the mouse hovers over the movie titles</p> <br>
 
**************************************************************************************************************************************************************************
<p><u>Project 3</u><p>
<p>In the Unix command line, please change the directory to "/var/lib/tomcat7/webapps/fabflix/source_XML_parsing" by pasting the below command:</p>
<p> cd /var/lib/tomcat7/webapps/fabflix/source_XML_parsing </p>
<p> This directory contains the XML parsers and and compilation script by the name of "compile.sh". Please place the XML files to be parsed in this directory and 
run the script using the command:</p>
<p> sudo sh -x compile.sh
<p> This script will compile all the Java files and also run the parsers.</p>
<p> Upon completion, log into MySQL using the user 'classta' and password 'classta' command: </p>
<p> mysql -u classta -p
<p> Enter the password when promted and check the tables for content </p>
<p><b> For Stored Procedure</b></p>
<p> A movie is considered to exist if all its required values (ie title, year and director) match</p> <br>

**************************************************************************************************************************************************************************

<p><u>Project 2</u></p>

<p> 1) It was deployed on AWS instance whose IP is 52.26.158.153   </p>
<p> 2) The user has 2 option when he goes to fablix website. Either user can sign in or can continue search as a guest.</p>
<p> 3) The user can browse a movie by Genre or by movie title. The user can also use Advanced search feature where he can search through Movie's Title, Year released, Director or by Star's name.</p>
<p> 4) In the movie list page, the user can select a single movie which will lead to a single movie page including complete details of the movie. At the bottom, there is a Add to Cart option where the user can purchase it. The user has to Sign in if visited as a guest to make a purchase.</p>
<p> 5) The Shopping cart shows the details of Movie Id, name, quantity and the price of the movie. The Cart has CheckOut button which takes the user to a page where the user has to enter Name, Last Name and Credit Card information. </p>
<p> 6) If all the details are correct, the confirmation page will be shown with successful transaction.</p>





 
</body>
</html>