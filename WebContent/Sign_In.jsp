<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="Style.css">
  <TITLE>FABFLIX Sign In Page</TITLE>
  <script src='https://www.google.com/recaptcha/api.js'></script>
  
</head>

<body onload="init()">
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

<!-- 
<form action="<%=request.getContextPath()%>/Simple_Search.jsp">
<input type="text" name="simple_search"><input type="submit" value="Search">
</form>   
<a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/Sign_In.jsp">Sign In</a>|<a href="<%=request.getContextPath()%>/ShoppingCart.jsp">My Cart</a>
-->

</td>

</tr>
</table>
</div>
<CENTER>
<H2 ALIGN=CENTER> Sign In </H2>
<H3 ALIGN=CENTER> Please enter your Email-ID and Password</H3>

<FORM ACTION="<%=request.getContextPath()%>/servlet/LoginPage"
      METHOD="POST">
  
  Email-ID: <INPUT TYPE="TEXT" NAME="email" required><BR>
  Password: <INPUT TYPE="PASSWORD" NAME="password" required><BR>
  <BR>
  <div class="g-recaptcha" data-sitekey="6LcpjB4TAAAAACxsCR0KwHMV07mZlOtzfUvGPb8c" ></div>
  <br>
    <INPUT TYPE="SUBMIT" VALUE="Login">
  
</FORM>
</CENTER>

</BODY>
</HTML>
