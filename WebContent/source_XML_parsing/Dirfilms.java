import java.util.*;

public class Dirfilms 
{
	
	private String dirid;
	private String is;
	private String castnote;
	Filmc filmc;
	
	public Dirfilms()
	{
		filmc = new Filmc();
	}
	
	public void setDirid(String d)
	{
		this.dirid = d;
	}
	
	public void setIs(String i)
	{
		this.is = i;
	}
	
	public void setFilmc(Filmc f)
	{
		this.filmc = f;
	}
	
	public void setCastnote(String c)
	{
		this.castnote = c;
	}
	
	public String getDirid()
	{
		return dirid;
	}
	
	public String getIs()
	{
		return is;
	}
	
	public String getCastnote()
	{
		return castnote;
	}
	
	public String Output()
	{
		StringBuffer s = new StringBuffer();
		/*s.append("Director ID: "+this.dirid);
		s.append("Is: "+this.is);
		s.append("Castnote: "+this.castnote);
		s.append("Filmc values: ");*/
		
		for(int i=0;i<filmc.Mc.size();i++)
		{
			MClass m = filmc.Mc.get(i);
			//s.append("F value: "+m.getF());
			//s.append("T value: "+m.getT());
			s.append("A value: "+m.getA());
			//s.append("P value: "+m.getP());
			//s.append("R value: "+m.getR());
			//s.append("Rname value: "+m.getRname());

			for (int k=0;k<m.facts.size();k++)
				s.append("Facts: "+m.facts.get(k));
			
			s.append("Error: "+m.getError());
		}
		return s.toString();	
	}
}