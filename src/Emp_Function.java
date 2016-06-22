
import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;


public class Emp_Function extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
	
	
	
	public static void InsertStar(HttpServletRequest request, HttpServletResponse response, Connection dbcon)throws Exception
	{
		String add_new_star=request.getParameter("add_new_star");
		String new_star_DOB=request.getParameter("new_star_DOB");
		String new_star_photoURL=request.getParameter("new_star_photoURL");
		String InsertSQL;
	    PrintWriter out = response.getWriter();
        
		int count=0;
		try
		{
			if(add_new_star.trim().equals(""))
			{
				out.println("<p>Please enter a star value</p>");
				
			}
			else
			{
			
			String first_name,last_name;
			if (add_new_star.indexOf(' ')>-1)
			{
				first_name = (add_new_star.substring(0,add_new_star.indexOf(' '))).trim();
				last_name = (add_new_star.substring(add_new_star.indexOf(' '),add_new_star.length())).trim();
			}
			else
			{
				first_name = "";
				last_name = add_new_star.trim();
			}
		
			
			//if the new star already exists then do not add the star details in the database
			
			
			
		    String queryCheck;
		    
		    PreparedStatement ps;
		    
		    if (new_star_DOB.equals("") && new_star_photoURL.equals("")){
		    	
		    	queryCheck="select * from stars where first_name= ? and last_name= ? and dob is null and photo_url is null ;";
		    	ps =dbcon.prepareStatement(queryCheck);
		    	 ps.setString(1, first_name);
				 ps.setString(2, last_name);
		    }
		    else if (new_star_DOB.equals("") && !new_star_photoURL.equals("")){
		    	queryCheck="select * from stars where first_name= ? and last_name= ? and dob is null and photo_url = ? ;";
		    	ps =dbcon.prepareStatement(queryCheck);
		    	ps.setString(1, first_name);
				ps.setString(2, last_name);
		    	ps.setString(3, new_star_photoURL);
		    }
		    else if (!new_star_DOB.equals("") && new_star_photoURL.equals("")){
		    	queryCheck="select * from stars where first_name= ? and last_name= ? and dob = ? and photo_url is null ;";
		    	ps =dbcon.prepareStatement(queryCheck);
		    	ps.setString(1, first_name);
				ps.setString(2, last_name);
		    	ps.setString(3, new_star_DOB);
		    }
		    else{
		    	queryCheck="select * from stars where first_name= ? and last_name= ? and dob = ? and photo_url = ? ;";
		    	ps =dbcon.prepareStatement(queryCheck);
		    	ps.setString(1, first_name);
				ps.setString(2, last_name);
		        ps.setString(3, new_star_DOB);
		        ps.setString(4, new_star_photoURL);
		    }
		   
		
		   
		    //print for reference
		    System.out.println(first_name+" "+last_name+" "+new_star_DOB+" "+new_star_photoURL);
			ResultSet result=ps.executeQuery();
			if(result.next())
			{
				System.out.println("Star exists");
				out.println("<br><h>Star already exists!!!<br>Enter new star details</h>");
			    return;
			}
			
			//insert new star details into table
			InsertSQL = "insert into stars (first_name, last_name, dob, photo_url) values (?,?,?,?);";
			PreparedStatement psinsert = dbcon.prepareStatement(InsertSQL);
			if (new_star_DOB.equals("") && new_star_photoURL.equals("")){
				psinsert.setString(1, first_name);
				psinsert.setString(2, last_name);
				psinsert.setString(3, null);
				psinsert.setString(4, null);}
		    
			else if (new_star_DOB.equals("") && !new_star_photoURL.equals("")){
				psinsert.setString(1, first_name);
				psinsert.setString(2, last_name);
		        psinsert.setString(3, null);
		        psinsert.setString(4, new_star_photoURL.trim());}
			else if (!new_star_DOB.equals("") && new_star_photoURL.equals("")){
				psinsert.setString(1, first_name);
				psinsert.setString(2, last_name);
				psinsert.setString(3, new_star_DOB.trim());
				psinsert.setString(4, null);}
	        else{
	        	psinsert.setString(1, first_name);
	        	psinsert.setString(2, last_name);
	        	psinsert.setString(3, new_star_DOB.trim());
	        	psinsert.setString(4, new_star_photoURL.trim());}
			 
			count = psinsert.executeUpdate();
			out.println("<p>No. of rows inserted into stars table: '"+count+"'</p>");
			if(count==1)
				out.println("<h>Stars table insertion successfull</h>");
			
			}
		}
			
		catch(SQLException e)
		{
			System.out.println("SQL error: "+e.getErrorCode()+" Message: "+e.getMessage());
			System.out.println("Operation unsuccessful");
		}
	}
	
	
	
	
	public static void Meta(HttpServletRequest request, HttpServletResponse response, Connection dbcon, Statement statement)throws Exception
	{	    
		
		PrintWriter out = response.getWriter();

		try
		{
			DatabaseMetaData dbmd = dbcon.getMetaData();
			ResultSet result = dbmd.getTables(null, null, null,null);
			out.println("<p>&nbsp;</p><center><h>Metadata of Tables in the Fabflix Database</h></center><p>&nbsp;</p>");
			while(result.next())
			{
				
				String table = result.getString(3);
				out.println("<table><th>TABLE:   "+table+"</th><tr>");
				ResultSet res = statement.executeQuery("describe "+table);
				out.println("<table border><th>COLUMN NAME:     </th><th>TYPE:     </th>");
				while(res.next())
					out.println("<tr><td>"+res.getString(1)+"</td><td>"+res.getString(2)+"</td></tr>");
				
				
				out.println("</table></tr><p>&nbsp;</p>");
				res.close();
			}
			out.println("</table>");
			result.close();
		}
		catch(SQLException e)
		{
			System.out.println("SQL error: "+e.getErrorCode()+" Message: "+e.getMessage());
		}
	}
	
	
	
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		
		if ((request.getSession(false).getAttribute("Emp_Name")) == null)
			response.sendRedirect(request.getContextPath()+"/Emp_Sign_In.jsp");
	    
		String action=request.getParameter("action");
	    
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

	    response.setContentType("text/html");    // Response mime type

	    // Output stream to STDOUT
	    PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Employee Action</TITLE></HEAD>");
		String html_layout="<body><div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><a href='"+request.getContextPath()+"/Emp_Sign_In.jsp'>Sign Out</a></td></table></div>";
		out.println(html_layout);
		out.println("<a href='"+request.getContextPath()+"/Emp_Dashboard.jsp'>Go back to Employee Dashboard</a>");
	    try
	    {
	    	dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        
	        switch(action)
	        {
	        
	        case "Add new star":
	        	InsertStar(request, response, dbcon);
	        	break;
	        
	        case "Get Metadata":
	        	Meta(request, response, dbcon, statement);
	        	break;
	        
	        case "Add Movie":
	        	response.sendRedirect(request.getContextPath()+"/Add_Movie.jsp");
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
