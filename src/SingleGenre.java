import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class SingleGenre extends HttpServlet
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
	    String genre = request.getParameter("genre");
	    String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a>|<a href='"+request.getContextPath()+"/servlet/Cart?id=-99'>Checkout</a></td></tr></table></div>";
        String html_layout_nosession="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/Sign_In.jsp'>Sign In</a></td></tr></table></div>";
        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    out.println("<h1 align='center'>Movies with the Genre '"+genre+"'</h1>");
	    String query = "select * from movies where id in (select movie_id from genres_in_movies where genre_id in (select id from genres where name='"+genre+"'))";
	    
	    out.println("<TABLE class='list'>");
        out.println("<center><tr>" +
        		  "<td>ID Number</td>" + 
           		  "<td>Movie Title</td>" + 
          		  "<td>Movie Year</td>" +
           		  "<td>Movie Director</td>" +
           		  "<td>Movie Banner</td>" +
           		  "<td>Movie Trailer</td></center>");
	    
	    try
	    {
	    	dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        ResultSet result = statement.executeQuery(query);
	        
	        while(result.next())
	        {
	        	String id = result.getString("id");
	            String t = result.getString("title");
	            String y = result.getString("year");
	            String dir = result.getString("director");
	            String bUrl = result.getString("banner_url");
	            String tUrl = result.getString("trailer_url");		  
	                  
	            out.println("<center><tr>" +
	            		"<td>" + id + "</td>" +
                        "<td><a href='"+request.getContextPath()+"/servlet/SingleMovie?id="+id+"'>" + t + "</td>" +
                        "<td>" + y + "</td>" +
                        "<td>" + dir + "</td>" +
                        "<td><a href='" + bUrl + "'>"+bUrl+"</a></td>" +
                        "<td><a href='" + tUrl + "'>"+tUrl+"</a></td>" +
                        "</tr></center>");
	        }
	        
	        if(dbcon != null)
      		  dbcon.close();
        	
      	  dbcon = null;
	        //out.println("</TABLE>");
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