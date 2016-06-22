<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="Style.css">
<script type="text/javascript" src="AjaxAutocomplete.js"></script>
<title>Main Page</title>
</head>

<body onload="init()">

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

<td style="padding-left:500px">



<!--  <form action="<%=request.getContextPath()%>/Simple_Search.jsp">
<input type="text" name="simple_search"><input type="submit" value="Search">
</form>  -->
<table>
<tr><td style="padding-left:100px">
     <a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/index.jsp">Sign Out</a>|<a href="<%=request.getContextPath()%>/servlet/Cart?id=-99">Checkout</a>
</td></tr>

<tr><td>
    <!-- AJAX Implementation -->
    <form name="autofillform" action="autocomplete">
   <table class="autosearch" border="0" cellpadding="5">
      <tr>
        <td><strong style="color: black;">Search :</strong></td>
        <td>
            <input type="text"
                    size="30"
                    id="complete-field"
                    onkeyup="doCompletion();"> 
        </td>
                                  
      </tr>
      <tr>
         <td id="auto-row" colspan="2">
         <table id="complete-table" class="popupBox"></table>
         </td>
       </tr>
  </table>
</form>
</td>
</tr>



</table>



</td>
</table>
</div>
<h1>Welcome <%=request.getSession(false).getAttribute("Name")%></h1>


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
<table>
<tr><td style="padding-left:100px">
     <a href="<%=request.getContextPath()%>/MainPage.jsp">Main</a>|<a href="<%=request.getContextPath()%>/SearchPage.jsp">Advanced Search</a>|<a href="<%=request.getContextPath()%>/Sign_In.jsp">Sign In</a>
</td></tr>
<tr><td>
    <!-- AJAX Implementation -->
    <form name="autofillform" action="autocomplete">
   <table class="autosearch" border="0" cellpadding="5">
      <tr>
        <td><strong style="color: black;">Search :</strong></td>
        <td>
            <input type="text"
                    size="30"
                    id="complete-field"
                    onkeyup="doCompletion();"> 
        </td>
                                  
      </tr>
      <tr>
         <td id="auto-row" colspan="2">
         <table id="complete-table" class="popupBox"></table>
         </td>
       </tr>
  </table>
</form>
</td>
</tr>


<!--  <form action="<%=request.getContextPath()%>/Simple_Search.jsp">
<input type="text" name="simple_search"><input type="submit" value="Search">
</form>  -->

</table>

</td>

</tr>
</table>
</div>
<%} %>

<h1 ALIGN='center'>What would you like to do?</h1>
<p>&nbsp;</p>
<table align='center'>
<tr><td><FORM ACTION="<%=request.getContextPath()%>/SearchPage.jsp"
      METHOD="GET">
    <INPUT class='search_button' TYPE="SUBMIT" VALUE=" Advanced Search">
</FORM></td>

<td><FORM ACTION="<%=request.getContextPath()%>/BrowsePage.jsp"
      METHOD="GET">
    <INPUT class='search_button' TYPE="SUBMIT" VALUE="Browse Movie">
</FORM></td></tr></table>

</body>
</html>