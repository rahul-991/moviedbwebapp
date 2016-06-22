import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class Emp_LoginPage extends HttpServlet
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and checks login information of the employer at fabflix";
    }
    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
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
	        
	        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
	        out.println("<BODY><H1>Sign-in Information</H1>");

	        try
	        {
	              dbcon = ds.getConnection();
	              // Declare our statement
	              Statement statement = dbcon.createStatement();
	              String emp_email = request.getParameter("emp_email");
	              String emp_password = request.getParameter("emp_password");
	              String query = "select fullname from employees where email='"+emp_email+"' and password='"+emp_password+"'";
	              String output;
	              ResultSet result = statement.executeQuery(query);
	              
	              if(result.next())
	              {
	            	  String emp_name = result.getString("fullname");
	            	  HttpSession session = request.getSession();
	            	  //String PrevURL = (String)session.getAttribute("PrevURL");
	            	  session.setAttribute("Emp_Name", emp_name);
	            	  response.sendRedirect(request.getContextPath()+"/Emp_Dashboard.jsp");

	              }
	            	  
	              else
	              {
	            	  output = "<H2 ALIGN=LEFT>Wrong Email-ID/Password combination </H2>";
	            	  out.println(output);
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
	        
	        out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	{
		doPost(request, response);
	}
}