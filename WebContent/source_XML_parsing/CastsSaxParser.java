import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.catalina.webresources.DirResourceSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class CastsSaxParser extends DefaultHandler 
{
	
	ArrayList<Dirfilms> casts;
	Dirfilms temp;
	String tempval;
	Filmc filmc;
	MClass temp1;
	
	public CastsSaxParser()
	{
		casts = new ArrayList();
	}
	
	public void run()
	{
		parseDocument();
		//printData();
	}
	
	public void printData()
	{			
		for(Dirfilms dir:casts)
			System.out.println(dir.Output());
	}
	
	private void parseDocument()
	{
		System.out.println("Parse function");
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try
		{
			SAXParser sp = spf.newSAXParser();
			sp.parse("casts124.xml", this);
		}
		
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}
		catch (IOException ie) 
		{
			ie.printStackTrace();
		}
	}
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		
		tempval = "";
		if(qName.equalsIgnoreCase("dirfilms"))
			temp = new Dirfilms();
		
		else if(qName.equalsIgnoreCase("filmc"))
			filmc = new Filmc();
		
		else if (qName.equalsIgnoreCase("m"))
			temp1 = new MClass();
		
	}
	
	
	public void characters(char ch[], int start, int length) throws SAXException
	{
		tempval = new String(ch, start, length);
	}
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		
		if(qName.equalsIgnoreCase("dirfilms"))
			casts.add(temp);
			
		else if (qName.equalsIgnoreCase("dirid")) 
			temp.setDirid(tempval);
		
		else if (qName.equalsIgnoreCase("is")) 
			temp.setIs(tempval);
		
		else if (qName.equalsIgnoreCase("castnote")) 
			temp.setCastnote(tempval);			
	
		else if (qName.equalsIgnoreCase("filmc"))
			temp.setFilmc(filmc);
		
		else if (qName.equalsIgnoreCase("m")) 
			filmc.AddtoList(temp1);
		
		else if (qName.equalsIgnoreCase("f")) 
			temp1.setF(tempval);
		
		else if (qName.equalsIgnoreCase("t")) 
			temp1.setT(tempval);
		
		else if (qName.equalsIgnoreCase("a")) 
			temp1.setA(tempval);
		
		else if (qName.equalsIgnoreCase("p")) 
			temp1.setP(tempval);
		
		else if (qName.equalsIgnoreCase("r")) 
			temp1.setR(tempval);
		
		else if (qName.equalsIgnoreCase("rname")) 
			temp1.setRname(tempval);
			
	}
	
	
	public Statement DBConnection() throws Exception
	{
		
		String loginUser = "root";
        String loginPasswd = "rasmysql";
        String loginUrl = "jdbc:mysql:///moviedb";
        
        //Class.forName("org.gjt.mm.mysql.Driver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        // Declare our statement
        Statement statement = dbcon.createStatement();
        return statement;
	}
	
	
	public void ParserLoad() throws Exception
	{
		
		Statement statement = DBConnection();
		Dirfilms dir = new Dirfilms();
		MovieXMLParse movie = new MovieXMLParse();
		movie.runExample();
		String movie_name = null;
		
		HashMap<String, Integer> movies = new HashMap<>();
		HashMap<String, Integer> actors = new HashMap<>();
		ResultSet result = statement.executeQuery("Select id,upper(trim(title)) from movies");
		MClass mc = new MClass();
		int count;
		
		while(result.next())
			movies.put(result.getString(2), result.getInt(1));
		result.close();
		
		result = statement.executeQuery("Select id, upper(trim(concat(first_name,' ',last_name))) from stars");
		while(result.next())
			actors.put(result.getString(2), result.getInt(1));
		result.close();
		String InsertQuery="";
		

		for(int i=0;i<casts.size();i++)
		{
			
			count=0;
			dir = casts.get(i);
			InsertQuery = "Insert into stars_in_movies(star_id, movie_id) values ";
			
			
			for(int j=0;j<dir.filmc.Mc.size();j++)
			{	
				
				mc = dir.filmc.Mc.get(j);
				for(MovieXML m:movie.Movie_ls)
				{	
					if (mc.getF().trim().toUpperCase().equals(m.getFid().toUpperCase()))
						movie_name = m.getTitle().trim().toUpperCase().replace("'", "");
				}
							
				if (movies.containsKey(movie_name) && actors.containsKey(mc.getA().toUpperCase().trim()))
				{
					count++;
					System.out.println("Movie_id has Star Star id (Movie_id, Star_id): "+actors.get(mc.getA().toUpperCase())+","+movies.get(movie_name));
					InsertQuery += "("+actors.get(mc.getA().toUpperCase())+","+movies.get(movie_name)+"),";
				}
			}
				
			if (count!=0)
			{
				InsertQuery = InsertQuery.substring(0,InsertQuery.length()-1);
				System.out.println("InsertQuery value: "+InsertQuery);
				System.out.println("The number of rows inserted: "+statement.executeUpdate(InsertQuery));
				System.out.println();
			}
			
		}
		
	}
	
	
	public static void main(String[] args) throws Exception
	{
		CastsSaxParser c = new CastsSaxParser();
		
		c.run();
		c.ParserLoad();
		
	}
}
