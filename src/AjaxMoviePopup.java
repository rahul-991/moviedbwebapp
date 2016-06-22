import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class AjaxMoviePopup
 */

public class AjaxMoviePopup  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	        //System.out.println("in the servlet");
		    String movie_id = request.getParameter("id");
		    //System.out.println(movie_id);
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    
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
		    
		    try
		    {
		        dbcon = ds.getConnection();
		        Statement statement = dbcon.createStatement();
		        String stars = "",genres = "";
		        ResultSet result3,result2 = null,result1 = statement.executeQuery("select * from movies where id='"+movie_id+"';");
		        while (result1.next()){
		        out.println("<p style='font-size: 30px;color:#02fd99;'><u><strong>"+result1.getString("title")+"</strong></u></p><br><p style='color:#fe593d;'><u>ID : </u></p><p>"+result1.getInt("id")+"</p><br><p style='color:#fe593d;'><u>Release Year : </u></p><p>"+result1.getInt("year")+"</p><br><p style='color:#fe593d;'><u>Director : </u></p><p>"+result1.getString("director")+"</p><br><p style='color:#fe593d;'><u>Banner : </u></p><p>"+result1.getString("banner_url")+"</p><br>");
		        }
		        
		        result2=statement.executeQuery("select concat(first_name,' ',last_name) from stars where id in (select star_id from stars_in_movies where movie_id='"+movie_id+"');");
		        while (result2.next()){
		        stars=stars+result2.getString(1)+", ";
		        }
		        stars="<p style='color:#fe593d;'><u>Stars : </u></p><p>"+stars.substring(0,(stars.length()-2))+"</p><br>";
		        out.println(stars);
		        
		        result3 = statement.executeQuery("select name from genres where id in (select genre_id from genres_in_movies where movie_id='"+movie_id+"');");
		        while (result3.next()){
		        genres=genres+result3.getString(1)+", ";
		        }
		        genres="<p style='color:#fe593d;'><u>Genre : </u></p><p>"+genres.substring(0,(genres.length()-2))+"</p><br>";
		        out.println(genres);
		     	
		        if(dbcon != null)
          		  dbcon.close();
	        	
          	  dbcon = null;
		        
		    //result.close();
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
	            out.println("<HTML>" +"<HEAD><TITLE>" +"MovieDB: Error" +"</TITLE></HEAD>\n<BODY>" +"<P>SQL error in doGet: " +ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
		    out.close();
		    	    
		    out.println("</TABLE></center>");  
		    }
		
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}


