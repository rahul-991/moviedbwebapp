
import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;



public class MovieList extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
    
	
	public static void PrintMovies(HttpServletRequest request, Connection dbcon, PrintWriter out, PreparedStatement ps1, String querystring) throws IOException, ServletException
	{
	    
		try
		{
            
			String URL = request.getRequestURI()+"?"+querystring;	        
	        Statement statement = dbcon.createStatement();
	        String  attribute = request.getParameter("attribute");
	        String order = request.getParameter("order");
	        String page = request.getParameter("page");
	        int pageno=0;
	        //out.println("<div id='pop' class='pop'></div>");
	        
	        String query = ps1.toString().substring(ps1.toString().indexOf("select"),ps1.toString().length());
		    if (attribute!=null && order!=null)
		    {
		    	if (attribute.equals("title"))
		    	{
		    		if (order.equals("asc"))
		    			query = query + "order by title";
		    		else
		    			query = query + "order by title desc";
		    	}
	        
		    	else if (attribute.equals("year"))
		    	{
		    		if (order.equals("asc"))
		    			query = query + "order by year";
		    		else
		    			query = query + "order by year desc";
		    	}
		    }
		    
		    if (page == null)
		    	pageno=0;
		    else
		    	pageno = Integer.parseInt(page);
		    ps1 = dbcon.prepareStatement(query);
		    //System.out.println("Prepared statement is: "+ps1);
		    ResultSet result = ps1.executeQuery();
		    
		    int rownum = 0;
		    while(result.next())
		    	rownum+=1;
		    result.close();
		    int totrows=rownum;
		    
		    query = ps1.toString().substring(ps1.toString().indexOf("select"),ps1.toString().length());
		    query = query + " limit 10 offset ?";
		    ps1 = dbcon.prepareStatement(query);
		    ps1.setInt(1, (pageno*10));
		    //System.out.println("Second PS: "+ps1.toString());
		    
		    result = ps1.executeQuery();
	        rownum = rownum - (pageno*10);
	        
	        Statement statement2 = dbcon.createStatement();
		    Statement statement3 = dbcon.createStatement();
		  
		    if (rownum!=0)
		    {
		    	
		    	out.println("<p>Sort:  <a href = '"+URL+"&attribute=title&order=asc'>Title"
		    			+ "<img src='"+request.getContextPath()+"/arrow-up-icon-download.jpg' height=20 width=20></a> | "
		        		+ " <a href = '"+URL+"&attribute=title&order=desc'> Title"
		        		+ "<img src='"+request.getContextPath()+"/down-arrow-icon-download.jpg' height=20 width=20></a> | " 
		        		+ "<a href = '"+URL+"&attribute=year&order=asc'> Year"
		        		+ "<img src='"+request.getContextPath()+"/arrow-up-icon-download.jpg' height=20 width=20></a> | "
		        		+ "<a href = '"+URL+"&attribute=year&order=desc'>Year"
		        		+ "<img src='"+request.getContextPath()+"/down-arrow-icon-download.jpg' height=20 width=20></a></p>");
		    	
		    	out.println("<center><TABLE border class='list'>");
		        out.println("<tr>" +
		        		  "<th>ID Number</th>" + 
		           		  "<th>Movie Title</th>" + 
		          		  "<th>Movie Year</th>" +
		           		  "<th>Movie Director</th>" +
		           		  "<th>Genres</th>" +
		           		  "<th>Actors</th>");
		    	
		    	while (result.next())
		    	{
		    		String id = result.getString("id");
		    		String t = result.getString("title");
		    		String y = result.getString("year");
		    		String dir = result.getString("director");
	            
		    		String query2 = "select name from genres where id in (select genre_id from genres_in_movies where movie_id="+id+")";
		    		String query3 = "select id, concat(first_name,' ',last_name) from stars where id in (select star_id from stars_in_movies where movie_id="+id+")";
	            
		    		ResultSet result2 = statement2.executeQuery(query2);
		    		ResultSet result3 = statement3.executeQuery(query3);
	            
		    		out.println("<tr>" +
		    				"<td>" + id + "</td>" +
		    				"<td><a id='"+id+"' href='"+request.getContextPath()+"/servlet/SingleMovie?id="+id+"' onmouseover='poop(this.id)' onmouseout='opo()'>"+t+"</a><div id='poop' class='pop'></div></td>" +
		    				"<td>" + y + "</td>" +
		    				"<td>" + dir + "</td>");
	                   	            
		    		out.println("<td>");
	
		    		while(result2.next())
		    		{
		    			String genre = result2.getString(1);
		    			out.println("<p>"+genre+"</p>");
		    		}
		    		
		    		out.println("</td><td>");
 
		    		while (result3.next())
		    		{
		    			String star_id = result3.getString(1);
		    			String star_name = result3.getString(2);
		    			out.println("<a href='"+request.getContextPath()+"/servlet/SingleStar?id="+star_id+"'>"+star_name+"</a>    ");
		    		}
	            
	            
		    		out.println("</td></tr>");
	            
		    		result2.close();
		    		result3.close();
	    		
		    	}
		    }
		    
		    else
		    	out.println("<h3 ALIGN=CENTER>No Search Results Found</h3>");
		    
		   	result.close();
		   	out.println("</TABLE></center>");  
		   	
		   	String test = request.getQueryString();
	        
	        if(test.contains("page"))
	        	test = (test.replace("&page=","")).replace(page,"");
	        	
	        URL = request.getRequestURI()+"?"+test;
	        if (totrows>0)
	        	out.println("<p ALIGN=CENTER>Page "+(pageno+1)+" of "+(int)(Math.ceil(totrows/10)+1)+" pages</p>");
	        
	        if (rownum>10)
	        {
	        	if (pageno==0)
	        		out.println("<p ALIGN=CENTER><a href='"+URL+"&page="+(pageno+1)+"'>NEXT PAGE</a></p>");
	        	else
	        		out.println("<p ALIGN=CENTER><a href='"+URL+"&page="+(pageno-1)+"'>PREVIOUS PAGE</a> | "
	        				+ "<a href='"+URL+"&page="+(pageno+1)+"'>NEXT PAGE</a></p>");
	        }
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
	                "<P>SQL error in doGet:jwrbworbwroubrob " +
	                ex.getMessage() + "</P></BODY></HTML>");
	        return;
	    }        
	}
	
	
	public void SearchResults(HttpServletRequest request, HttpServletResponse response, Connection dbcon, Statement statement,String html_layout_session,String html_layout_nosession) throws IOException, ServletException, SQLException
	{
		
		PrintWriter out = response.getWriter();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		String title = request.getParameter("title");
	    String year = request.getParameter("year");
	    String director = request.getParameter("director");
	    String name = request.getParameter("name");
	    int flag=0;
	    String filter = null;
	    
	    String querystring = "title="+title+"&year="+year+"&director="+director+"&name="+name+"&action=Search";
	        
	    if(name.trim()!="")
	    {
	    	if(name.trim().indexOf(' ')>-1)
	    		filter = "(select movie_id from stars_in_movies where star_id in (select id from stars where upper(concat(first_name,' ',last_name)) like '%"+name.toUpperCase()+"%'))";
	    	else
	    		filter = "(select movie_id from stars_in_movies where star_id in (select id from stars where upper(first_name) like '%"+name.toUpperCase()+"%' or upper(last_name) like '%"+name.toUpperCase()+"%'))";
	    }
	    
	    else if (name.trim() == "")
	    	filter = "(select movie_id from stars_in_movies where star_id in (select id from stars))";
	    
	    String query = "select * from movies where director like ? and year like ? and title like ? and id in "+filter;
	    ps1 = dbcon.prepareStatement(query);
	    
	    if(director.trim()!="")
	    	ps1.setString(1, "%"+director.trim()+"%");
	    
	    else if(director.trim() == "")
	    	ps1.setString(1,"%" );
	    
	    if(year.trim()!="")
	    	ps1.setString(2, "%"+year.trim()+"%");
	    
	    else if (year.trim() == "")
	    	ps1.setString(2,"%");
	    
	    if(title.trim()!="")
	    	ps1.setString(3, "%"+title.trim()+"%");
	    
	    else if(title.trim() == "")
	    	ps1.setString(3, "%");
	    	    	        
	    //out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD><body>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    out.println("<H1 align='center'>Search Results: </H1>");
	    PrintMovies(request,dbcon,out,ps1,querystring);
	    
	}
	
	
	
	public static void BrowseGenre(HttpServletRequest request, HttpServletResponse response, Connection dbcon, Statement statement,String html_layout_session,String html_layout_nosession) throws IOException, ServletException, SQLException
	{
		
		PreparedStatement ps1 = null;
		PrintWriter out = response.getWriter();
	    String genre = request.getParameter("genres");
	    String query = "select * from movies where id in (select movie_id from genres_in_movies where genre_id in (select id from genres where name = ? ))";
	    String querystring = "genres="+genre+"&action=Browse+genre";
	   
	    ps1 = dbcon.prepareStatement(query);
	    //out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    out.println("<H2 align='center'>Movies with the genre: "+genre+"</H1><BR>" + "<BR>");
	    
	    ps1.setString(1, genre);
	    PrintMovies(request,dbcon,out,ps1,querystring);
	    
	    out.close(); 
	}
	
	
	
	public static void BrowseTitle(HttpServletRequest request, HttpServletResponse response, Connection dbcon, Statement statement,String html_layout_session,String html_layout_nosession) throws IOException, ServletException, SQLException
	{
		
		PrintWriter out = response.getWriter();
	    String title = request.getParameter("titles");
	    PreparedStatement ps1 = null;
	    
	    String query = "select * from movies where title like ?";
	    String querystring = "titles="+title+"&action=Browse+Title";
	    ps1 = dbcon.prepareStatement(query);
	    
	    //out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
	    if(request.getSession(false).getAttribute("Name")!=null)
	    	out.println(html_layout_session);
	    else
	    	out.println(html_layout_nosession);
	    out.println("<H2 align='center'>Movies with the title: "+title+"</H1><BR>" + "<BR>");
	    
	    ps1.setString(1, title+"%");
	    PrintMovies(request,dbcon,out,ps1,querystring);
	    
	    out.close(); 
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		
		long servlet_startTime = System.nanoTime();
		
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
	    
	    String action=request.getParameter("action");

	    response.setContentType("text/html");    // Response mime type
	    
	    // Output stream to STDOUT <TITLE>Search Results</TITLE></HEAD>
	    PrintWriter out = response.getWriter();
	    out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><script type='text/javascript' src='"+request.getContextPath()+"/AjaxAutocomplete.js'></script><TITLE>Search Results</TITLE></HEAD><body onload='init()'>");
	    
	    try
	    {
	    	long jdbc_startTime = System.nanoTime();
	    	dbcon = ds.getConnection();
	    	long jdbc_endTime = System.nanoTime();
	    	long jdbc_elapsedTime = jdbc_endTime - jdbc_startTime;
	    	
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a>|<a href='"+request.getContextPath()+"/servlet/Cart?id=-99'>Checkout</a></td></tr></table></div>";
	        String html_layout_nosession="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/Sign_In.jsp'>Sign In</a></td></tr></table></div>";
	      
	        switch(action)
	        {
	        
	        case "Search":
	        	SearchResults(request, response, dbcon, statement, html_layout_session,html_layout_nosession);
	        	long servlet_endTime = System.nanoTime();
	    	    long servlet_elapsedTime=servlet_endTime-servlet_startTime;
	    	    String TS_TJ="TS "+servlet_elapsedTime+" TJ "+jdbc_elapsedTime;
	    	    System.out.println(TS_TJ);
	        	break;
	        
	        case "Browse genre":
	        	BrowseGenre(request, response, dbcon, statement, html_layout_session, html_layout_nosession);
	        	break;
	        
	        case "Browse Title":
	        	BrowseTitle(request, response, dbcon, statement,html_layout_session, html_layout_nosession);
	        	break;
	        	
	        default:
	        	System.out.println("What the fudge??");
	        	
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	{
		doPost(request, response);
	}
}