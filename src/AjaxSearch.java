

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.StringTokenizer;

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
 * Servlet implementation class AjaxAutocomplete
 */

public class AjaxSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public AjaxSearch() {
        super();
        
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		    String action = request.getParameter("action");
		    String SearchString = request.getParameter("SearchString");
		   // response.setContentType("text/plain");
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		   
		    
		    if (action.equals("complete")&&(!SearchString.equals(""))) {

		        // check if user sent empty string
		 
		 // Split the string using StringTokenizer
		    String delims = " ";
	        String query = "select * from movies where title like ";

			StringTokenizer st = new StringTokenizer(SearchString, delims);
			int tokens=st.countTokens();
			System.out.println("Number of tokens = "+tokens);
			if(tokens==1)
			{
				
			String onlyword=st.nextToken();
	       query=query+"'"+onlyword+"%' or title like '% "+onlyword+"%';";
			}
			
	        //System.out.println("StringTokenizer Output outside: " + st.nextElement());
			if(tokens>1){
				query=query+"'%"+st.nextToken()+"%';";
				//while (st.hasMoreElements()) {
					for(int i=2;i<=tokens;i++)
					{
						if(i==tokens){
							String lasttoken=st.nextToken();
							query=query.substring(0, (query.length())-1);
							query+= " and (title like '"+lasttoken+"%' or title like '% "+lasttoken+"%');";
							
						}
						else{
							query=query.substring(0, (query.length())-1);
							query+=" and title like '%"+st.nextToken()+"%';";
						}
							
					}
				//System.out.println("StringTokenizer Output: " + st.nextElement());
				/*System.out.println(query);
				query=query.substring(0, (query.length())-1);
				System.out.println("After removing semicolon "+query);
				query=query+" and title like '%"+st.nextElement()+"%';";*/
				
										
			}
				
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
			
			System.out.println("final query"+query);
		    //query=query+";";
		    
		    
		    try
		    {
		        dbcon = ds.getConnection();
		        Statement statement = dbcon.createStatement();
		        ResultSet result = statement.executeQuery(query);
		        //out.print("<table>");
		        while(result.next())
		        {		        	                                
		        	 out.println("<tr><td class='popupCell'><a href='"+request.getContextPath()+"/servlet/SingleMovie?id="+result.getInt("id")+"' class='popupItem'>"+result.getString("title")+"</a></td></tr>");
		        } 	            
		        
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
	            //out.println("<HTML>" +"<HEAD><TITLE>" +"MovieDB: Error" +"</TITLE></HEAD>\n<BODY>" +"<P>SQL error in doGet: " +ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
		    //out.close();
		    	    
		    //out.println("</TABLE></center>");  
		    }
	}
		
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
