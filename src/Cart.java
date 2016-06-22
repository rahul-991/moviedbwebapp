import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class Cart extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves Single Movie results";
    }
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		
	    String UserName=null;
	    response.setContentType("text/html");    // Response mime type
	    HashMap<Integer,Integer> cart;
	    // Output stream to STDOUT
	    
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
	    int price=0;
	    try
	    {
		    String html_layout_session="<div class='menu'> <table><tr><td><img src='"+request.getContextPath()+"/fab logo.png' alt='logo' height=120 width=120></td> <td><p class='title'>FABFLIX<p></td><td style='padding-left:400px'><!--<form action='"+request.getContextPath()+"/Simple_Search.jsp'><input type='text' name='simple_search'><input type='submit' value='Search'></form>--><a href='"+request.getContextPath()+"/MainPage.jsp'>Main</a>|<a href='"+request.getContextPath()+"/SearchPage.jsp'>Advanced Search</a>|<a href='"+request.getContextPath()+"/index.jsp'>Sign Out</a></tr></table></div>";
	        out.println("<HTML><HEAD><link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/Style.css'><TITLE>Search Results</TITLE></HEAD>");
		    out.println(html_layout_session);
		    	
	    	//Class.forName("org.gjt.mm.mysql.Driver");
	        
			HttpSession session = request.getSession(false);
			dbcon = ds.getConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
			String id = request.getParameter("id");
	        UserName = (String)session.getAttribute("Name");
	    	
	        out.println("<h2>Shopping Cart for "+UserName+"</h2>");
	        ShoppingCart shoppingcart = (ShoppingCart)session.getAttribute("shoppingcart");
	        
	        if(null == shoppingcart)
	        {
	        	session.setAttribute("shoppingcart", new ShoppingCart());
	        	shoppingcart = new ShoppingCart();
	        	if (Integer.parseInt(id) == -99)
	        	{
	        		out.println("<p>Your shopping cart is empty</p>");
	        		out.println("<td><h3 ALIGN=LEFT><a href='"+request.getContextPath()+"/MainPage.jsp'>Main Page</a></h3></td>");
	        		return;
	        	}
	        }
	        
	        
	        if(id != null && Integer.parseInt(id)!= -99)
	        	shoppingcart.AddToCart(Integer.parseInt(id),1);
	        
	        if (shoppingcart!=null)
	        	session.setAttribute("shoppingcart",shoppingcart);
	        
	        
	        cart = shoppingcart.CartItems;
	        String action = request.getParameter("action");
			//String qty = request.getParameter("qty");
			String update_id = request.getParameter("update_id");
	        
	        
	        if(action!=null && action.equals("UPDATE") && shoppingcart!=null)
	        {
	        	int index = 0;
	        	String qs[] = (request.getQueryString()).split("&qty");
	        	for (int i=0;i<qs.length;i++)
	        	{
	        		if(qs[i].contains("UPDATE"))
	        			index=i;
	        	}
	        	
	        	String quant[] = request.getParameterValues("qty");
	        	String update_ids[] = request.getParameterValues("update_id");
	        	
	        	shoppingcart.Update(Integer.parseInt(update_ids[index]), Integer.parseInt(quant[index]));
	        	session.setAttribute("shoppingcart", shoppingcart);
	        	cart = shoppingcart.CartItems;
	        }
	        
	        else if(action!=null && action.equals("delete") && shoppingcart!=null)
	        {
	        	shoppingcart.Delete(Integer.parseInt(update_id));
	        	session.setAttribute("shoppingcart", shoppingcart);
	        	cart = shoppingcart.CartItems;
	        }
	        
	        else if(action!=null && action.equals("cartdelete") && shoppingcart!=null)
	        {
	        	cart.clear();
	        	shoppingcart.CartItems.clear();
	        	session.setAttribute("shoppingcart", shoppingcart);
	        }
	        
	        if(!cart.isEmpty())
	        {
		        out.println("<p>The contents of the shopping cart are: </p>");
		        out.println("<center><TABLE border class='list'>");
		        out.println("<tr>" +
		        		"<th>ID Number</th>" + 
			           	"<th>Movie Title</th>" + 
			          	"<th>Quantity</th>"+"<th>Updated Quantity</th>"+"<th>Price</th>"+"<th>Delete</th>");
		        shoppingcart = (ShoppingCart)session.getAttribute("shoppingcart");
		        cart = shoppingcart.CartItems;
		        
		        for(int key : cart.keySet())
		        {
		        	String name=null;
		        	ResultSet result = statement.executeQuery("select title from movies where id="+key);
		        	price += (10*cart.get(key));
		        	
		        	while(result.next())
		        		name = result.getString(1);
		        	
		        	out.println("<FORM ACTION='"+request.getContextPath()+"/servlet/Cart' METHOD=GET>");
		        	out.println("<tr>" +
		        			"<td>" + key + "</td>" +
		        			"<td>" + name + "</td>" +
		        			"<td><INPUT TYPE=NUMBER NAME=qty VALUE="+cart.get(key)+"></td>"+
		        			"<td><INPUT TYPE=SUBMIT NAME=action VALUE=UPDATE>"+
		        			"<INPUT TYPE=HIDDEN NAME=update_id VALUE="+key+">"+
		        			"<td>$"+(10*cart.get(key))+".00</td>"+
		        			"<td><a href='"+request.getRequestURI()+"?action=delete&id=-99&update_id="+key+"'>DELETE</td></tr>");		        
		        }
		        
		        out.println("</TABLE></center>");
		        out.println("<h2 ALIGN=CENTER>TOTAL PRICE: $"+price+".00</h2>");
		        out.println("<p ALIGN=CENTER><a href ='"+request.getContextPath()+"/servlet/Cart?id=-99&action=cartdelete'>CLICK TO EMPTY CART</a></p>");
		        
		        out.println("<p ALIGN=LEFT><a href='"+request.getContextPath()+"/MainPage.jsp'>Main Page</a></p>"
		        		+ "<p ALIGN=RIGHT><a href='"+request.getContextPath()+"/Checkout.jsp'>Checkout</a></p>");
	        
	        }
	        
	        else
	        {
	        	out.println("<p>Shopping cart is empty</p>");
	        	out.println("<td><h3 ALIGN=LEFT><a href='"+request.getContextPath()+"/MainPage.jsp'>Main Page</a></h3></td>");
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
	  		doGet(request, response);
	  	}
}