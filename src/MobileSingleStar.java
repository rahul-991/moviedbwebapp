import java.io.*;
import java.sql.*;
import java.util.Enumeration;

import javax.json.JsonArray;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MobileSingleStar extends HttpServlet
{
    
	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and checks login information of the user";
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
	        ds = (DataSource)envCtx.lookup("jdbc/moviedb");
		} 
        catch (NamingException e) 
        {
			e.printStackTrace();
		}
        Connection dbcon = null;
 
	        JSONArray jsonarray = new JSONArray();
	        JSONObject json = new JSONObject();
	        PrintWriter out = response.getWriter();
	        int id = 0;
	        
	        try
	        {
	            dbcon = ds.getConnection();
	              // Declare our statement
	            Statement statement = dbcon.createStatement();
	            String name = request.getParameter("name");
	            name = name.trim();
	            ResultSet result = statement.executeQuery("select * from stars where upper(concat(first_name,' ',last_name))='"+name.toUpperCase()+"'");
	            
	            
	            while(result.next())
	            {
	            	json.put("id", result.getString(1));
	            	id = Integer.parseInt(result.getString(1));
	            	json.put("name", name);
	            	json.put("dob", result.getString(4));
	            	json.put("Photo_url", result.getString(5));
	            }
	            
	            //jsonarray.add(json);
	            result.close();
	            
	            result = statement.executeQuery("select title from movies where id in (select movie_id from stars_in_movies where star_id="+id+")");
	            
	            
	            JSONArray jsonarr = new JSONArray();
	            while(result.next())
	            	jsonarr.add(result.getString(1));
	            
	            json.put("movies", jsonarr);
	            jsonarray.add(json);
	            
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
	        
	        response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.write(jsonarray.toString());
		        
		    out.close();
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws IOException, ServletException
		{
			doGet(request, response);
		}
	}