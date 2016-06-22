import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class Checkout extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		
		HttpSession session = request.getSession(false);
		
	    HashMap<Integer,Integer> cart;
	    response.setContentType("text/html");    // Response mime type

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
	    
	    // Output stream to STDOUT
	    PrintWriter out = response.getWriter();
	    
	    String fname = request.getParameter("fname");
	    String lname = request.getParameter("lname");
	    String cc = request.getParameter("cc_num");
	    String exp_day = request.getParameter("exp_day");
	    String exp_month = request.getParameter("exp_month");
	    String exp_year = request.getParameter("exp_year");
	    fname = fname.trim().toUpperCase();
	    lname = lname.trim().toUpperCase();
	    String exp_date = exp_year+"-"+exp_month+"-"+exp_day;
	    String query = "select id from customers where trim(upper(first_name))='"+fname+"' and trim(upper(last_name))='"+lname+"'";
	    int flag=0;
	    String sale_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    ShoppingCart shoppingcart = (ShoppingCart)session.getAttribute("shoppingcart");
        cart = shoppingcart.CartItems;
        
	    
	    try
	    {
	    	
	    	String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a></tr></table></div>";
	        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
		    out.println(html_layout_session);
		  
	    	//Class.forName("org.gjt.mm.mysql.Driver");
	        dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	        Statement s2 = dbcon.createStatement();
	        ResultSet result = statement.executeQuery(query);
	        int cust_id = Integer.parseInt((String)session.getAttribute("cust_id"));
	        while(result.next())
	        {
	        	if (result.getInt(1) == cust_id)
	        	{
	        		flag=1;
	        		break;
	        	}
	        }
	        
	        if (flag==0)
	        {
	        	out.println("<h2>Credit card not matched with user in customer database</h2>");
	        	out.println("<form action='"+request.getContextPath()+"/Checkout.jsp'><input type=submit value=RETRY></form>");
	        	return;
	        }
	        result.close();
	        
	        query = "select * from creditcards where trim(upper(first_name))='"+fname+"' and trim(upper(last_name))='"+lname+"' and id='"+cc
		    		+"' and expiration='"+exp_date+"'";
	        result = statement.executeQuery(query);
	        System.out.println("The query is: "+query);
	        if(result.next())
	        {
	        	out.println("<h1>Card details confirmed, purchase confirmed</h1>");
	        	out.println("<p><a href='"+request.getContextPath()+"/MainPage.jsp'>BACK TO MAIN PAGE</a></p>");
	        }
	        else
	        {
	        	out.println("<h2>Wrong information, please enter the details again</h2>");
	        	out.println("<form action='"+request.getContextPath()+"/Checkout.jsp'><input type=submit value=RETRY></form>");
	        }
	        result.close();
	        
	        int affrows=0;
	        
	        for(int key:cart.keySet())
	        {
	        	int saleid=0;
	        	result = s2.executeQuery("select max(id) from sales");
	        	
	        	if(result.next())
	        		saleid = result.getInt(1);
	        	saleid +=1;
	        	System.out.println("max id: "+saleid);
	        	query = "insert into sales (id,customer_id, movie_id, sale_date) values ("+saleid+","+cust_id
	        		+","+key+",'"+sale_date+"')";
	        	System.out.println("sales query is: "+query);
	        	affrows += statement.executeUpdate(query);
	        }
	        
	        if(affrows == 0)
	        	System.out.println("Did not update sales table: "+affrows);
	        	
	        else
	        {	
	        	System.out.println("Updated sales table: "+affrows);
	        	ShoppingCart sc = (ShoppingCart)session.getAttribute("shoppingcart");
	        	sc.CartItems.clear();
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