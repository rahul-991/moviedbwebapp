import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionFilter implements javax.servlet.Filter 
{

    public void destroy() 
    {}

    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
    {

    	HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) res;
    	String url = request.getServletPath();

    	boolean allowedRequest = true;
    	System.out.println("filter: "+url+" contains: "+url.contains("/servlet/Cart"));
    	if (url.contains("/servlet/Cart") || url.contains("/Checkout"))
    		allowedRequest = false;

    	if (!allowedRequest) 
    	{
    		HttpSession session = request.getSession(false);
    		if (null == session.getAttribute("Name")) 
    		{
    			session.setAttribute("PrevURL",request.getRequestURI()+"?"+request.getQueryString());
    			response.sendRedirect(request.getContextPath()+"/Sign_In.jsp");
    			return;
    		}
    	}

    	chain.doFilter(req, res);
    }

    public void init(FilterConfig fConfig) throws ServletException 
    {

    }
}