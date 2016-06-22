<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<link rel="stylesheet" type="text/css" href="Style.css">
<BODY>
<%if(request.getSession(false).getAttribute("Name")!=null) { %>

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
<a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/index.jsp">Sign Out</a>|<a href="<%=request.getContextPath()%>/servlet/Cart?id=-99">Checkout</a>

</td>

</tr>
</table>
</div>
<%}

else {%>
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
<a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/Sign_In.jsp">Sign In</a>


</td>

</tr>
</table>
</div>
<%} %>

<h3>Search for a movie: </h3><br>

<FORM ACTION="<%=request.getContextPath()%>/servlet/MovieList"
      METHOD="GET">
 
<table>
 <tr> <td> Title: </td><td><INPUT TYPE="TEXT" NAME="title"></td></tr>
 <tr> <td>Year: </td><td><INPUT TYPE="TEXT" NAME="year"></td></tr>
 <tr><td>Director: </td><td><INPUT TYPE="TEXT" NAME="director"></td></tr>
 <tr><td>Star's name: </td><td><INPUT TYPE="TEXT" NAME="name"></td></tr>
 <tr><td><INPUT TYPE="SUBMIT" NAME="action" VALUE="Search"></td></tr>

</table>
</FORM>
<div class="footer">
Click  <a href="<%=request.getContextPath()%>/BrowsePage.jsp">  here  </a>    for browsing movies based on genres or title.
<p>All Rights Reserved</p>
</div>
</body>
</html>