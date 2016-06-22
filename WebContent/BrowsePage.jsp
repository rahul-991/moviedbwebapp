<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 javax.sql.DataSource,
 javax.naming.Context,
javax.naming.InitialContext,
javax.naming.NamingException"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="Style.css">
<title>Fabflix Browse</title>
</head>

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
<p>&nbsp;</p>
<p align='center' id="browse_page_heading">Browse by Genre or Title</p>

<table align='center'>
<tr><FORM ACTION="<%=request.getContextPath()%>/servlet/MovieList"
      METHOD="GET">
      <% 
      Context initCtx;
      Context envCtx;
      DataSource ds = null;
		
      try 
      {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
	        ds = (DataSource)envCtx.lookup("read");
		} 
      catch (NamingException e) 
      {
			e.printStackTrace();
		}
      Connection dbcon = null;
try{
//Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = ds.getConnection();
       Statement statement = connection.createStatement() ;

       ResultSet result = statement.executeQuery("select name from genres");
%>
<p>&nbsp;</p>
<td>
<center>

        <select class='drop_button' name="genres">
        <%  while(result.next()){ 
        String genre=result.getString("name"); 
        %>
            <option value="<%=genre%>"><%= genre %></option>
        <% } %>
        </select>
</center>
<%result.close();
statement.close();

if(dbcon != null)
	  dbcon.close();

dbcon = null;

        }
        catch(Exception e)
        {
             out.println("wrong entry"+e);
        }
%>

    <INPUT class='drop_button' TYPE="SUBMIT" NAME="action" VALUE="Browse genre"><BR></td>
  <td>  <select class='drop_button' name="titles">
        
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
            <option value="D">D</option>
            <option value="E">E</option>
            <option value="F">F</option>
            <option value="G">G</option>
            <option value="H">H</option>
            <option value="I">I</option>
            <option value="J">J</option>
            <option value="K">K</option>
            <option value="L">L</option>
            <option value="M">M</option>
            <option value="N">N</option>
            <option value="O">O</option>
            <option value="P">P</option>
            <option value="Q">Q</option>
            <option value="R">R</option>
            <option value="S">S</option>
            <option value="T">T</option>
            <option value="U">U</option>
            <option value="V">V</option>
            <option value="W">W</option>
            <option value="X">X</option>
            <option value="Y">Y</option>
            <option value="Z">Z</option>
           
        </select>
        <BR>
    
    <INPUT class='drop_button' TYPE="SUBMIT" NAME="action" VALUE="Browse Title">
  </td>
</FORM></tr>
</table>


</body>
</html>