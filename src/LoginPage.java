
import java.io.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class LoginPage extends HttpServlet
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and checks login information of the user";
    }
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
	        response.setContentType("text/html"); 
            
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
	        PrintWriter out = response.getWriter();
	       
	        //check for recaptcha
	        // Output stream to STDOUT
	        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
	    	System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
	    	// Verify CAPTCHA.
	    	boolean valid = VerifyUtils.verify(gRecaptchaResponse);
	    	if (!valid) {
	    	    //errorString = "Captcha invalid!";
	    	    out.println("<HTML>" +
	    			"<HEAD><TITLE>" +
	    			"MovieDB: Error" +
	    			"</TITLE></HEAD>\n<BODY>" +
	    			"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
	    	    return;
	    	}
	    	
	        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
	        out.println("<BODY><H1>Sign-in Information</H1>");

	        try
	        {

		        //Class.forName("org.gjt.mm.mysql.Driver");
	              //Class.forName("com.mysql.jdbc.Driver").newInstance();
	        	//System.out.println("datasource: "+ds);
	              dbcon = ds.getConnection();
	              //System.out.println("after ds");
	              // Declare our statement
	              Statement statement = dbcon.createStatement();
	              String email = request.getParameter("email");
	              String password = request.getParameter("password");
	              String query = "select first_name, last_name, id from customers where email='"+email+"' and password='"+password+"'";
	              String output;
	              ResultSet result = statement.executeQuery(query);
	              
	              if(result.next())
	              {
	            	  String name = result.getString(1)+" "+result.getString(2);
	            	  HttpSession session = request.getSession();
	            	  String PrevURL = (String)session.getAttribute("PrevURL");
	            	  session.setAttribute("cust_id", result.getString(3));
	            	  session.setAttribute("Name", name);
	            	  if(session.getAttribute("PrevURL") == null)
	            		  response.sendRedirect(request.getContextPath()+"/MainPage.jsp");
	            	  else
	            		  response.sendRedirect(PrevURL);
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