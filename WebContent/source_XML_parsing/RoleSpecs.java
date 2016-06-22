
public class RoleSpecs 
{
	
	private String rbase;
	private int rnumber;
	private String episode;
	private String sings;
	
	RoleSpecs()
	{}
	
	void setRbase(String r)
	{
		this.rbase = r;
	}
	
	void setRnumber(int r)
	{
		this.rnumber = r;
	}
	
	void setEpisode(String e)
	{
		this.episode = e;
	}
	
	void setSings(String s)
	{
		this.sings = s;
	}
	
	String getRbase()
	{
		return rbase;
	}
	
	int getRnumber()
	{
		return rnumber;
	}
	
	String getEpisode()
	{
		return episode;
	}
	
	String getSings()
	{
		return sings;
	}
}
