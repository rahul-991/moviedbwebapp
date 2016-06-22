import java.util.ArrayList;
public class MClass
{
	
	private String f,t,a,p,r,rname,error;
	ArrayList facts;
	
	MClass()
	{
		facts = new ArrayList();
	}
	
	void setF(String f1)
	{
		f = f1;
	}
	
	void setT(String t1)
	{
		t = t1;
	}
	
	void setA(String a1)
	{
		a = a1;
	}
	
	void setP(String p1)
	{
		p = p1;
	}
	
	void setR(String r1)
	{
		r = r1;
	}
	
	void setRname(String rn)
	{
		rname = rn;
	}
	
	void setError(String err)
	{
		error = err;
	}
		
	void setNotes(ArrayList notes)
	{
		this.facts = notes;
	}

	
	String getF()
	{
		return f;
	}
	
	String getT()
	{
		return t;
	}
	
	String getA()
	{
		return a;
	}
	
	String getP()
	{
		return p;
	}
	
	String getR()
	{
		return r;
	}
	
	String getRname()
	{
		return rname;
	}
	
	String getError()
	{
		return error;
	}
}
