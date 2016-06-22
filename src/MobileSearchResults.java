
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

public class MobileSearchResults extends HttpServlet
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	        PrintWriter out = response.getWriter();
	        
	        try
	        {
	              
	        	dbcon = ds.getConnection();
	              // Declare our statement
	              Statement statement = dbcon.createStatement();
	              
	              String title = request.getParameter("title");
	              int page = Integer.parseInt(request.getParameter("page"))	;
	              
	              String query = "select * from movies where upper(trim(title)) like '%"+title+"%' limit 10 offset "+page*10;
	              
	              ResultSet result = statement.executeQuery(query);
	              
	              while (result.next())
	              {
	            	  JSONObject json = new JSONObject();
	            	  json.put("id", result.getString(1));
	            	  json.put("title",result.getString(2));
	            	  json.put("year", result.getString(3));
	            	  json.put("director", result.getString(4));
	            	  json.put("b_url", result.getString(5));
	            	  json.put("t_url", result.getString(6));
	            	  jsonarray.add(json);
	            	  
	              }
	              
            	  result.close();
            	  statement.close();
            	  
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