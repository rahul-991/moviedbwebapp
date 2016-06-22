<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%		if ((request.getSession(false).getAttribute("Emp_Name")) == null)
	response.sendRedirect(request.getContextPath()+"/Emp_Sign_In.jsp"); %>
<link rel="stylesheet" type="text/css" href="Style.css">
<title>Add Movie Details</title>
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
<a href="<%=request.getContextPath()%>/Emp_Sign_In.jsp">Sign Out</a>
</td>

</tr>
</table>
</div>



<%if(request.getSession(false).getAttribute("Emp_Name")!=null) { %>



<p>&nbsp;</p>

<center>

    <h3>Add movie details</h3><br>
</center>

<FORM ACTION="<%=request.getContextPath()%>/servlet/AddMovie"
      METHOD="GET">
 
<table align="center">
 <tr> <td>Movie Title: </td><td><INPUT TYPE="TEXT" NAME="title" required></td></tr>
 <tr> <td>Year: </td><td><INPUT TYPE="TEXT" NAME="year" required></td></tr>
 <tr><td>Director: </td><td><INPUT TYPE="TEXT" NAME="director" required></td></tr>
 <tr><td>Banner URL: </td><td><INPUT TYPE="TEXT" NAME="banner_url"></td></tr>
 <tr><td>Trailer URL: </td><td><INPUT TYPE="TEXT" NAME="trailer_url"></td></tr>
 <tr><td>Genre: </td><td><INPUT TYPE="TEXT" NAME="genre" required></td></tr>
 <tr><td>Star name: </td><td><INPUT TYPE="TEXT" NAME="star_name" required></td></tr>
 <tr><td>Star DOB: </td><td><INPUT TYPE="DATE" NAME="star_dob" style="width: 160px;"></td></tr>
 <tr><td>Photo URL: </td><td><INPUT TYPE="TEXT" NAME="photo_url"></td></tr>
</table>

<center><INPUT TYPE="SUBMIT" NAME="action" VALUE="Add_Movie"></center>
</FORM>
</body>
</html>

<%}
else{%>

<p>Please login as Employee to access Employee Dashboard</p>
<a href="<%=request.getContextPath()%>/Emp_Sign_In.jsp">Employee Sign In</a>
</body>
</html>
<%}%>
