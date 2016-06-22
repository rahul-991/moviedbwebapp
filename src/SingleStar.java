import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class SingleStar extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
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

	    response.setContentType("text/html");    // Response mime type

	    // Output stream to STDOUT
	    PrintWriter out = response.getWriter();
	    String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a>|<a href='"+request.getContextPath()+"/servlet/Cart?-99'>Checkout</a></td></tr></table></div>";
        String html_layout_nosession="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/Sign_In.jsp'>Sign In</a></td></tr></table></div>";
        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    String star_id = request.getParameter("id");
	    String query = "select * from stars where id="+star_id;
	    String query2 = "select id, title from movies where id in (select movie_id from stars_in_movies where star_id ="+star_id+")";
	    
	    try
	    {
	    	dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        ResultSet result = statement.executeQuery(query);
	        
	        while(result.next())
	        {
	        	out.println("<h1>Details of the star '"+result.getString(2)+" "+result.getString(3)+"':</h1>");
	        	String name = result.getString("first_name")+" "+result.getString("last_name");
	        	String dob = result.getString("dob");
	        	String photoURL = result.getString("photo_url");
            
	        	Statement statement2 = dbcon.createStatement();
	        	ResultSet result2 = statement2.executeQuery(query2);
	        	
	        	out.println("<p><b>ID: </b>"+star_id+"</p>");
	        	out.println("<p><b>NAME: </b>"+name+"</p>");
	        	out.println("<p><b>DATE OF BIRTH: </b>"+dob+"</p>");
	        	out.println("<p><b>PHOTO: </b><a href='"+photoURL+"'>"+photoURL+"</a></p>");
	        	
	        	while(result2.next())
	        	{
	        		String movie_id = result2.getString(1);
	        		String movie_name = result2.getString(2);
	        		out.println("<p>*<a href='"+request.getContextPath()+"/servlet/SingleMovie?id="+movie_id+"'>"+movie_name+"</a></p>");
	        	}
	        	
	        	out.println("<br></br>");
	        	result2.close();
	        }
	        result.close();
	        
	        if(dbcon != null)
      		  dbcon.close();
        	
      	  dbcon = null;
      	  
        }
	    catch (SQLException ex) 
        {
              while (ex != null) 
              {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
              }
        }
        
        catch(java.lang.Exception ex)
        {
            out.println("<HTML>" +
                        "<HEAD><TITLE>" +
                        "MovieDB: Error" +
                        "</TITLE></HEAD>\n<BODY>" +
                        "<P>SQL error in doGet: " +
                        ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
	    out.println("</TABLE></center>");  
        out.println("<div class='footer'>Click  <a href='"+request.getContextPath()+"/BrowsePage.jsp'>  here  </a>    for browsing movies based on genres or title.<p>All Rights Reserved</p></div></body></html>");
        out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
	{
		doPost(request, response);
	}
}