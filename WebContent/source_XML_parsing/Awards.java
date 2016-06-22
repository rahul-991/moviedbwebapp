import java.util.*;

public class Awards
{
	ArrayList award;
	
	public Awards()
	{
		award = new ArrayList();
	}
	
	public void AddAward(String a)
	{
		award.add(a);
	}
	
	public ArrayList GetAwards()
	{
		return award;
	}
	
}

