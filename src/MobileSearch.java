
import java.io.*;
import java.sql.*;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.json.simple.JSONObject;

public class MobileSearch extends HttpServlet
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
 
	        JSONObject json = new JSONObject();
	        PrintWriter out = response.getWriter();
	        
	        try
	        {
	        		dbcon = ds.getConnection();
	              // Declare our statement
	              Statement statement = dbcon.createStatement();
	              
	              String title = request.getParameter("title");
	              String query = "select count(*) from movies where upper(trim(title)) like '%"+title+"%'";
	              ResultSet result = statement.executeQuery(query);
	              int res;
	              
	              if(result.next())
	              {
	            	  json.put("info", "success");
	            	  res = result.getInt(1);
	            	  json.put("results", res);
	              }
	              
	              else
	            	  json.put("info","error");
	              

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
	        out.write(json.toString());
	        
	        out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	{
		doGet(request, response);
	}
}