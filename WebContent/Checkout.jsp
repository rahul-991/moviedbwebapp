<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Style.css">
<title>Check Out</title>
</head>

<body>
<BODY>
<%if(request.getSession(false).getAttribute("Name") == null) { 
response.sendRedirect(request.getContextPath()+"/Sign_In.jsp");}%>
<div class="menu"> 
<table>
<tr>
<td>
<img src="fab logo.png" alt="logo" height=120 width=120>
</td> 
   
<td>
<p class="title">FABFLIX<p>
</td>

<td style="padding-left:400px">

<!--  <form action="<%=request.getContextPath()%>/Simple_Search.jsp">
<input type="text" name="simple_search"><input type="submit" value="Search">
</form>  -->
<a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">
Advanced Search</a>|<a href="<%=request.getContextPath()%>/index.jsp">Sign Out</a>

Checkout</a>

</td>

</tr>
</table>
</div>

<table>
<FORM ACTION="<%=request.getContextPath()%>/servlet/Checkout"
      METHOD="POST">
 
 <tr><td>First Name on Card: </td><td><INPUT TYPE="TEXT" NAME="fname" REQUIRED></td></tr>
 <tr><td>Last Name on Card: </td><td><INPUT TYPE="TEXT" NAME="lname" REQUIRED></td></tr>
 <tr><td>Credit Card Number: </td><td><INPUT TYPE="NUMBER" NAME="cc_num" REQUIRED></td></tr>
 <tr><td>Card Expiry: </td><td>YEAR:<INPUT TYPE="NUMBER" NAME="exp_year" REQUIRED></td><td>
 <td>MONTH:<INPUT TYPE="NUMBER" NAME="exp_month" REQUIRED></td><td>DAY:<INPUT TYPE="NUMBER" NAME="exp_day" REQUIRED></td></tr>
 <tr><td><INPUT TYPE="SUBMIT" NAME="action" VALUE="Checkout"></td></tr>
</FORM>
</table>

</body>
</html>