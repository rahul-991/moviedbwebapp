
public class Life
{
	
	private String debut;
	private String last;
	private String lifenote;
	private int fno;
	
	Life()
	{}
	
	void setDebut(String d)
	{
		this.debut = d;
	}
	
	
	void setLast(String l)
	{
		this.last = l;
	}
	
	
	void setFno(int fn)
	{
		this.fno = fn;
	}
	
	
	void setLifenote(String ln)
	{
		this.lifenote = ln;
	}
	
	
	String getDebut()
	{
		return debut;
	}
	
	String getLast()
	{
		return last;
	}
	
	int getFno()
	{
		return fno;
	}
	
	String getLifenote()
	{
		return lifenote;
	}
}