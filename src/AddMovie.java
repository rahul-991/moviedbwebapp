import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class AddMovie extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
	
	public static String addMovie(String title,String year,String director, String banner_url,String trailer_url,Connection dbcon,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		Statement statement = dbcon.createStatement();
		Statement select = dbcon.createStatement();
		String InsertSQL;
		String checkquery;
		PrintWriter out = response.getWriter();
		checkquery="select * from movies where upper(title) ='"+title.toUpperCase()+"';)";
		java.sql.ResultSet result2,result1=statement.executeQuery(checkquery);
		
		//check if movie already exists
		if(result1.next())
		{
			out.println("<h1>This movie already exists</h1><br><a href='"+request.getContextPath()+"/Add_Movie.jsp'>Go Back to Add Movie<a>");
			return null;
		}
		
		
		if (banner_url.equals(null) && trailer_url.equals(null))
			InsertSQL="insert into movies (title,year,director,banner_url,trailer_url) values ('"+title+"','"+year+"','"+director+"',"+null+","+null+");";
		else if (banner_url.equals("") && !trailer_url.equals(""))
			InsertSQL="insert into movies (title,year,director,banner_url,trailer_url) values ('"+title+"','"+year+"','"+director+"',"+null+",'"+trailer_url.trim()+"');";
		else if (!banner_url.equals("") && trailer_url.equals(""))
			InsertSQL="insert into movies (title,year,director,banner_url,trailer_url) values ('"+title+"','"+year+"','"+director+"','"+banner_url.trim()+"',"+null+");";
		else
				InsertSQL="insert into movies (title,year,director,banner_url,trailer_url) values ('"+title+"','"+year+"','"+director+"','"+banner_url.trim()+"','"+trailer_url.trim()+"');";
		int count = select.executeUpdate(InsertSQL);
		System.out.println("No. of rows inserted into movies table: "+count);
		
		//retrieving value of id from movies table
		String query_id="select id from movies where title='"+title+"';";
		result2=select.executeQuery(query_id);
		String movie_id = result2.getString("id");
		return movie_id;
	}
	
	
	/*public static void addStar(String star_name,String star_dob,String photo_url,Connection dbcon,HttpServletResponse response,String movie_id) throws Exception
	{
	
		String InsertSQL;
	    PrintWriter out = response.getWriter();
	    Statement select = dbcon.createStatement();
	    
		int count=0;
		try
		{
			if(star_name.trim().equals(""))
			{
				out.println("<p>Please enter a star value</p>");
				
			}
			else
			{
			
			String first_name,last_name;
			if (star_name.indexOf(' ')>-1)
			{
				first_name = (star_name.substring(0,star_name.indexOf(' '))).trim();
				last_name = (star_name.substring(star_name.indexOf(' '),star_name.length())).trim();
			}
			else
			{
				first_name = "";
				last_name = star_name.trim();
			}
		
			if (star_dob.equals("") && photo_url.equals(""))
				InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values ('"+first_name+"','"+last_name+"',"+null+","+null+")";
			else if (star_dob.equals("") && !photo_url.equals(""))
				InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values ('"+first_name+"','"+last_name+"',"+null+",'"+photo_url.trim()+"')";
			else if (!star_dob.equals("") && photo_url.equals(""))
				InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values ('"+first_name+"','"+last_name+"','"+star_dob.trim()+"',"+null+")";
			else
				InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values ('"+first_name+"','"+last_name+"','"+star_dob.trim()+"','"+photo_url.trim()+"')";
			
			//InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values ('"+first_name+"','"+last_name+"',"+new_star_DOB+","+new_star_photoURL+")";
			count = select.executeUpdate(InsertSQL);
			out.println("<p>No. of rows inserted into stars table: '"+count+"'</p>");
			if(count==1)
				out.println("<h>Stars table insertion successfull</h>");
			
			//retrieving value of id from stars table
			String query_id="select id from stars where concat(first_name,last_name)='"+first_name+last_name+"';";
			java.sql.ResultSet result=select.executeQuery(query_id);
			String star_id = result.getString("id");
			Statement stmt=dbcon.createStatement();
			int num=stmt.executeUpdate("insert into stars_in_movies values('"+star_id+"','"+movie_id+"';");
			if (num==1)
				out.println("<h>Stars_in_movies table insertion successfull</h>");
			
			}
		}
			
		catch(SQLException e)
		{
			System.out.println("SQL error: "+e.getErrorCode()+" Message: "+e.getMessage());
			System.out.println("Operation unsuccessful");
		}
		
		return;
	}*/
	
	
	/*public static void addGenre(String genre,Connection dbcon,String movie_id,HttpServletResponse response) throws Exception
	{
		try{
		Statement select = dbcon.createStatement();
		String InsertSQL= "insert into genres (name) values('"+genre+"');";
		int count = select.executeUpdate(InsertSQL);
		System.out.println("No. of rows inserted into genres table: "+count);
		PrintWriter out = response.getWriter();
		if(count==1)
			out.println("<h>Genres table insertion successfull</h>");
		
		
		String query_id="select id from genres where name='"+genre+"';";
		java.sql.ResultSet result=select.executeQuery(query_id);
		String genres_id = result.getString("id");
		Statement stmt=dbcon.createStatement();
		int num=stmt.executeUpdate("insert into genres_in_movies values('"+genres_id+"','"+movie_id+"';");
		if(num==1)
			out.println("<h>Genres_in_movies table insertion successfull</h>");
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL error: "+e.getErrorCode()+" Message: "+e.getMessage());
			System.out.println("Operation unsuccessful");
		}
		
	}*/
	
	
	public void add_movie(HttpServletRequest request, HttpServletResponse response, Connection dbcon) throws Exception
	{
		String title=request.getParameter("title").trim();
        String year=request.getParameter("year").trim();
        String director=request.getParameter("director").trim();
        String banner_url=request.getParameter("banner_url").trim();
        String trailer_url=request.getParameter("trailer_url").trim();
        String genre=request.getParameter("genre").trim();
        String star_name=request.getParameter("star_name").trim();
        String star_dob=request.getParameter("star_dob").trim();
        String photo_url=request.getParameter("photo_url").trim();
        
        PrintWriter out = response.getWriter();
        
		
		String first_name,last_name;
		if (star_name.indexOf(' ')>-1)
		{
			first_name = (star_name.substring(0,star_name.indexOf(' '))).trim();
			last_name = (star_name.substring(star_name.indexOf(' '),star_name.length())).trim();
		}
		else
		{
			first_name = "";
			last_name = star_name.trim();
		}
	
        
        /*if(banner_url.equals(""))
        	banner_url=null;
        if(trailer_url.equals(""))
        	trailer_url=null;
        if(star_dob.equals(""))
        	star_dob=null;
        if(photo_url.equals(""))
        	photo_url=null;*/
        try
        {
        CallableStatement cStmt = dbcon.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?,?,?)}");
        cStmt.setString(1,title);
        cStmt.setString(2,year);
        cStmt.setString(3,director);
        
        if(banner_url.equals("")){
        	cStmt.setString(4,null);}
        else{
            cStmt.setString(4,banner_url);}
        
        if(trailer_url.equals("")){
        	cStmt.setString(5,null);}
        else{
            cStmt.setString(5,trailer_url);}
        
        cStmt.setString(6,first_name);
        cStmt.setString(7,last_name);
        
        if(star_dob.equals("")){
            cStmt.setString(8,null);}
        else{
        	cStmt.setString(8,star_dob);}
        
        
        if(photo_url.equals("")){
        cStmt.setString(9,null);}
        else{
            cStmt.setString(9,photo_url);}
    
        
        cStmt.setString(10,genre);
    
        cStmt.registerOutParameter(11, java.sql.Types.INTEGER);
        cStmt.executeUpdate();
        
        //get status from stored procedure
        int status = cStmt.getInt(11);
        
        if (status==1)
        {
        	out.println("<br><h>Movie Inserted successfully.</h><br>Table movie, stars and other related tables have been updated.");
        }
        else
        {
        	out.println("<br><h>Movie Exists already</h><br>Please enter new movie details.");           
        }
        
        //String movie_id=addMovie(title,year,director,banner_url,trailer_url,dbcon,response,request);
       // if(movie_id.equals(null))
        //   return;	
        
        //addStar(star_name,star_dob,photo_url,dbcon,response,movie_id);
        //addGenre(genre,dbcon,movie_id,response);
        }
        
        
        catch(SQLException e)
		{
			System.out.println("SQL error: "+e.getErrorCode()+" Message: "+e.getMessage());
			System.out.println("Operation unsuccessful");
		}
        
	}
	
	
	
	
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		if ((request.getSession(false).getAttribute("Emp_Name")) == null)
			response.sendRedirect(request.getContextPath()+"/Emp_Sign_In.jsp");
	    
	    String action=request.getParameter("action");

	    response.setContentType("text/html");    // Response mime type

	    // Output stream to STDOUT
	    PrintWriter out = response.getWriter();
	    
	    Context initCtx;
        Context envCtx;
        DataSource ds = null;
		
        try 
        {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
	        ds = (DataSource)envCtx.lookup("write");
		} 
        catch (NamingException e) 
        {
			e.printStackTrace();
		}
        Connection dbcon = null;
        System.out.println("datasource: "+ds);
	    
	    try
	    {
	    	
	        dbcon = ds.getConnection();
	        // Declare our statement
	        
	        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Employee Action</TITLE></HEAD>");
			String html_layout="<body><div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><a href='"+request.getContextPath()+"/Emp_Sign_In.jsp'>Sign Out</a></td></table></div>";
			out.println(html_layout);
			out.println("<a href='"+request.getContextPath()+"/Emp_Dashboard.jsp'>Go back to Employee Dashboard</a><br><br>");
			out.println("<a href='"+request.getContextPath()+"/Add_Movie.jsp'>Enter Movie Details</a>");
	        
	        if (action.equals("Add_Movie"))
	        {
	        	add_movie(request, response, dbcon);
	        	
	        }
	        
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

}
}
