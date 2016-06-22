import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
public class SingleMovie extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves Single Movie results";
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
	    String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a>|<a href='"+request.getContextPath()+"/servlet/Cart?id=-99'>Checkout</a></td></tr></table></div>";
        String html_layout_nosession="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/Sign_In.jsp'>Sign In</a></td></tr></table></div>";
        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    out.println("<h1 align='center'><b>Movie Information: </b></h1>");
	    String id = request.getParameter("id");
	    String query = "select * from movies where id="+id;
	    
	    try
	    {
	    	dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        ResultSet result = statement.executeQuery(query);
	        
	        while(result.next())
	        {
	        	
	        	String t = result.getString("title");
	        	String y = result.getString("year");
	        	String dir = result.getString("director");
	        	String tUrl = result.getString("trailer_url");
            
	        	Statement statement2 = dbcon.createStatement();
	        	query = "select name from genres where id in (select genre_id from genres_in_movies where movie_id="+id+")";
	        	ResultSet result2 = statement2.executeQuery(query);
	        	
	        	Statement statement3 = dbcon.createStatement();
	        	query = "select id, concat(first_name,' ',last_name) from stars where id in (select star_id from stars_in_movies where movie_id="+id+")";
	        	ResultSet result3 = statement3.executeQuery(query);
            
	        	out.println("<p><b>ID: </b>"+id+"</p>");
	        	out.println("<p><b>TITLE: </b>"+t+"</p>");
	        	out.println("<p><b>YEAR: </b>"+y+"</p>");
	        	out.println("<p><b>DIRECTOR: </b>"+dir+"</p>");
	        	out.println("<p><b>TRAILER: </b><a href = '"+tUrl+"'>"+tUrl+"</a></p>");
	        	out.println("<p><b>GENRES: </b></p>");
	        	
	        	while(result2.next())
	        	{
	        		String gen = result2.getString(1);
	        		out.println("<p>*<a href='"+request.getContextPath()+"/servlet/SingleGenre?genre="+gen+"'>"+gen+"</a></p>");
	        	}
	        		
	        	out.println("<p><b>STARS: </b></p>");
	        	
	        	while(result3.next())
	        	{
	        		String star_id = result3.getString(1);
	        		String name = result3.getString(2);
	        		out.println("<p>*<a href='"+request.getContextPath()+"/servlet/SingleStar?id="+star_id+"'>"+name+"</a></p>");
	        	}
	        	
	        	out.println("<br></br>");
	        	result2.close();
	        	result3.close();
	        }
	        result.close();
	        out.println("<p><a href='"+request.getContextPath()+"/servlet/Cart?id="+id+"'>ADD TO CART</a></p>");
	        
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
	    //out.println("</TABLE></center>");  
        out.println("<div class='footer'>Click  <a href='"+request.getContextPath()+"/BrowsePage.jsp'>  here  </a>    for browsing movies based on genres or title.<p>All Rights Reserved</p></div></body></html>");
        out.close();
	}
}