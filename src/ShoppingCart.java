
import java.util.HashMap;

public class ShoppingCart
{
	
	public String getServletInfo()
    {
		return "Servlet connects to MySQL database and retrieves search results";
    }
	
	public HashMap<Integer, Integer> CartItems;
    
    public ShoppingCart()
    {
     CartItems = new HashMap<>();
    }
    
    public void AddToCart(Integer itemId, int qty)
    {
    	if(CartItems.containsKey(itemId))
    	{
    		int value = CartItems.get(itemId);
    		value++;
    		CartItems.put(itemId, value);
    	}
    	else
    	{
    		CartItems.put(itemId, qty);
    	}
    }
    
    public void Update(Integer itemId, int qty)
    {
    	CartItems.put(itemId, qty);
    }
    
    public void Delete(Integer itemId)
    {
        CartItems.remove(itemId);
    }
}