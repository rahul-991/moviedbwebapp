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
</form>  -->
<!-- a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/index.jsp">Sign Out</a>|<a href="<%=request.getContextPath()%>/servlet/Cart?id=-99">Checkout</a>-->
<a href="<%=request.getContextPath()%>/Emp_Sign_In.jsp">Sign Out</a>
</td>

</tr>
</table>
</div>



<%if(request.getSession(false).getAttribute("Emp_Name")!=null) { %>
<h1>Welcome <%=request.getSession(false).getAttribute("Emp_Name")%></h1>


<p>&nbsp;</p>
<center>



 
 
 
 <table><tr>
 <td><FORM ACTION="<%=request.getContextPath()%>/servlet/Emp_Function"
      METHOD="GET">
      
    Star Name </td> <td>:<INPUT TYPE="TEXT"  NAME="add_new_star" style="width: 150px;height: 30px;" required><br></td></tr>
   <tr> <td>Star Dob </td><td>:<INPUT TYPE="DATE"  NAME="new_star_DOB" style="width: 150px;height: 30px;"><br></td></tr>
   <tr> <td> Star PhotoURL </td><td>:<INPUT TYPE="TEXT"  NAME="new_star_photoURL" style="width: 150px;height: 30px;"><br></td></tr>
   </table>
    <center> <INPUT TYPE="SUBMIT" NAME="action" VALUE="Add new star" style="width: 100px;height: 30px;"></center>
 <p>&nbsp;</p>   


<INPUT class='search_button' TYPE="SUBMIT" NAME="action" VALUE="Get Metadata" formnovalidate></FORM>
<p>&nbsp;</p> 
<FORM ACTION="Add_Movie.jsp"
      METHOD="GET">
    <INPUT class='search_button' TYPE="SUBMIT" NAME="action" VALUE="Add Movie" formnovalidate></FORM></center>

</body>
</html>

<%}
else{%>

<p>Please login as Employee to access Employee Dashboard</p>
<a href="<%=request.getContextPath()%>/Emp_Sign_In.jsp">Employee Sign In</a>
</body>
</html>
<%}%>
