
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ActorsSaxHandler extends DefaultHandler implements ErrorHandler 
{
	
		   boolean bFamilyName = false;
		   boolean bFirstName = false;
		   boolean bDob = false;
		   
		   List<Actor> Actor_ls;
			
		   private String tempVal;
			
			
		   private Actor temp;
			
			
		   public ActorsSaxHandler()
		   {
			   Actor_ls = new ArrayList<Actor>();
		   }
			
		   public void runExample() 
		   {
			   parseDocument();
			   //printData();
		   }
		   
		   private void parseDocument() 
		   {
				
			   //get a factory
			   SAXParserFactory spf = SAXParserFactory.newInstance();
			   try 
			   {
				   //get a new instance of parser
				   SAXParser sp = spf.newSAXParser();
					
				   //parse the file and also register this class for call backs
				   sp.parse("actors63.xml", this);
					
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
			
		   	public void warning(SAXParseException e) throws SAXException 
		   	{
		   		System.out.println("WARNING : " + e.getMessage()); 
			}

			public void error(SAXParseException e) throws SAXException 
			{
				System.out.println("ERROR : " + e.getMessage());
				throw e;
			}

			public void fatalError(SAXParseException e) throws SAXException 
			{
				System.out.println("FATAL : " + e.getMessage());
				throw e;
			}

			private void printData()
			{
		    	
				System.out.println("No of Actors '" + Actor_ls.size() + "'.");
				
				Iterator<Actor> it = Actor_ls.iterator();
				while(it.hasNext())
					System.out.println(it.next().Output());
			}
			
		   @Override
		   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
		   {
			  
			   tempVal = "";
			   //System.out.println("Starting element is :"+qName);
			   if(qName.equalsIgnoreCase("actor"))
				   temp = new Actor();
			   
		   }

		   public void endElement(String uri, String localName, String qName) throws SAXException 
		   {
		        
			   //System.out.println("End Element :" + qName);
			   String s = "";
			   String st = "";
		 
			   if(qName.equalsIgnoreCase("actor"))
				   Actor_ls.add(temp);
							
			   else if (qName.equalsIgnoreCase("stagename")) 
				   temp.setStageName(tempVal);
					
			   else if (qName.equalsIgnoreCase("familyname"))
			   {
				
				   if(!tempVal.trim().equals(""))
					   temp.setFamilyName(tempVal);
				   else
				   {
					   s = temp.getStageName();				
					   s= s.substring((s.indexOf(" ")+1), s.length());					
					   temp.setFamilyName(s);
				   }
			   }
			   
			   else if (qName.equalsIgnoreCase("firstname")) 
			   {
				   if(!tempVal.trim().equals(""))
					   temp.setFirstName(tempVal);
					
				   else
				   {
					   st = temp.getStageName();
					   if(st.indexOf(" ") == -1)
						   temp.setFirstName(st);
					   else
					   {
						   st = st.substring(0,st.indexOf(" "));
						   temp.setFirstName(st);
					   }
					}
			   }
			   
			   else if (qName.equalsIgnoreCase("dob"))
			   {							
				   try
				   {													
					   temp.setDob(Integer.parseInt(tempVal.trim()));
				   }
				   catch(Exception e) 
				   {	
					   if (tempVal.trim().equals(""))
						   tempVal = "(Empty value)";
					   System.out.println("Invalid value for date, resetting this value to null: "+tempVal);
					   temp.setDob(0);
				   }	
			   } 
		   }

		   

		   public void characters(char[] ch, int start, int length) throws SAXException 
		   {
			   tempVal = new String(ch,start,length);
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
				int count=0;
				ArrayList<String> StarList = new ArrayList<>();
				String first_name, last_name, dob;
				
				ResultSet result = statement.executeQuery("Select first_name, last_name from stars");
				while(result.next())
					StarList.add(result.getString(1).trim().toUpperCase()+result.getString(2).trim().toUpperCase());
				result.close();
				
				String StarQuery = "Insert into stars (first_name, last_name, dob) values";
				
				//Actor actor = new Actor();
				for (Actor actor:Actor_ls)
				{
					if (count%20 == 0)
						StarQuery = "Insert into stars (first_name, last_name, dob) values";
					count+=1;
					try
					{
						
						first_name = actor.getFirstName().trim().replace("'","");
						last_name = actor.getFamilyName().trim().replace("'","");
						dob = actor.getDob();
						
						if (StarList.contains(first_name.trim().toUpperCase()+last_name.trim().toUpperCase()))
							continue;
						
						if (dob!=null)
						{
							dob = dob + "-01-01";
							if (!StarQuery.contains("('"+first_name+"','"+last_name+"','"+dob+"'),"))
								StarQuery = StarQuery + "('"+first_name+"','"+last_name+"','"+dob+"'),";
							else
								continue;
						}
						
						else
						{
							if (!StarQuery.contains("('"+first_name+"','"+last_name+"',null),"))
								StarQuery = StarQuery + "('"+first_name+"','"+last_name+"',"+dob+"),";
							else
								continue;
						}
						
						//System.out.println("The details of the star are: "+first_name+","+last_name+","+dob);
						
						if (count%20 == 0)
						{
							StarQuery = StarQuery.substring(0,(StarQuery.length()-1));
							System.out.println("InsertQuery is: "+StarQuery);
							System.out.println("The number of rows updated in stars table: "+statement.executeUpdate(StarQuery));
							
							StarList.clear();
							result = statement.executeQuery("Select first_name, last_name from stars");
							while(result.next())
								StarList.add(result.getString(1).trim().toUpperCase()+result.getString(2).trim().toUpperCase());
							result.close();
							
						}
						
					}
					
					catch(SQLException ex)
				    {
				    	while (ex != null) 
				        {
				    		System.out.println ("SQL Exception:  " + ex.getMessage ());
				            ex = ex.getNextException ();
				        }
				    }
				    catch(java.lang.Exception ex)
				    {
				    	System.out.println("Error: "+ex.getMessage());
				    }
				}
				
				if (count%20 != 0)
				{
					StarQuery = StarQuery.substring(0,(StarQuery.length()-1));
					System.out.println("The number of rows updated: "+statement.executeUpdate(StarQuery));
				}
			}
		   
		   
		   public static void main(String[] args) throws Exception
		   {
			   ActorsSaxHandler spe = new ActorsSaxHandler();
			   spe.runExample();
			   spe.ParserLoad();
		   }
}