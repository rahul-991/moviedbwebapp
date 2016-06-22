<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<link rel="stylesheet" type="text/css" href="Style.css">
  <TITLE>FABFLIX Welcome</TITLE>
 <!--   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  -->
</HEAD>
<% if(request.getSession(false) != null)
	{ session.invalidate();}  %>
<BODY>
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
</form>  
<a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/Sign_In.jsp">Sign In</a>|<a href="<%=request.getContextPath()%>/servlet/Cart">Checkout</a>
<a href="<%=request.getContextPath()%>/_dashboard">Employee Sign in</a>-->

</td>

</tr>
</table>
</div>
<p>&nbsp;</p>
<CENTER>
<table>
<tr>
<td> <form action="Sign_In.jsp">
<button class="search_button">Sign In</button>
</form></td>
<td> <form action="MainPage.jsp">
<button class="search_button">Continue as guest</button>
</form></td></tr></table>
</CENTER>
</BODY>
</HTML>