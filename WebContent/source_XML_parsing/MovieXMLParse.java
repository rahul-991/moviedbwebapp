import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;



public class MovieXMLParse  extends DefaultHandler
{
	
	List<MovieXML> Movie_ls;
	private String tempVal;
	private MovieXML temp;
	
	
	public MovieXMLParse()
	{
		Movie_ls = new ArrayList<MovieXML>();
	}
	
	public void runExample() 
	{
		parseDocument();
		//printData();
	}

	private void parseDocument() 
	{

		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{

			SAXParser sp = spf.newSAXParser();
			sp.parse("mains243.xml", this);
			
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

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData()
	{
		System.out.println("No of Movies '" + Movie_ls.size() + "'.");
		
		Iterator<MovieXML> it = Movie_ls.iterator();
		while(it.hasNext()) 
			System.out.println(it.next().Output());
		
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		tempVal = "";
		if(qName.equalsIgnoreCase("film")) 
			temp = new MovieXML();
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if(qName.equalsIgnoreCase("film")) 
			Movie_ls.add(temp);
		
		else if (qName.equalsIgnoreCase("dirn")) 
			temp.setDirector(tempVal);

		else if (qName.equalsIgnoreCase("t"))
			temp.setTitle(tempVal);

		else if (qName.equalsIgnoreCase("year")) 
			temp.setYear(tempVal);

		else if (qName.equalsIgnoreCase("cat")) 
			temp.AddCategory(tempVal);
		
		else if (qName.equalsIgnoreCase("fid")) 
			temp.setFid(tempVal);
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
		String title, director;
		int count=0, count1=0;
		String year;
		ArrayList<String> genre = new ArrayList<>();
		Statement statement = DBConnection();
		String MoviesQuery = "Insert into movies (title, year, director, banner_url, trailer_url) values ";
		String GenreQuery = "Insert into genres (name) values";
		String GenreinMoviesQuery = "Insert into genres_in_movies (genre_id, movie_id) values";
		ArrayList<String> genres = new ArrayList<>();
		ArrayList<String> movielist = new ArrayList<>();
		ResultSet result = statement.executeQuery("select upper(trim(name)) from genres");
		while(result.next())
			genres.add(result.getString(1));
		result.close();
		
		result = statement.executeQuery("Select upper(trim(title)),year,upper(trim(director)) from movies");
		while (result.next())
			movielist.add(result.getString(1)+result.getString(2)+result.getString(3));
		result.close();
		
		for(MovieXML m:Movie_ls)
		{
			
			try
			{
				if(count%20 == 0)
					MoviesQuery = "Insert into movies (title, year, director, banner_url, trailer_url) values";
				
				count+=1;
				//MovSelect =  "Select upper(trim(title)),year,upper(trim(director)) from movies";
				title = m.getTitle();
				if (title.contains("'"))
					title = title.replace("'", "");
				year = m.getYear().trim();
				director = m.getDirector();
				genre = m.cat;
				
				//System.out.println("the movies query is: "+MovSelect);
				//result = statement.executeQuery(MovSelect);
				//result.close();
				
				if (movielist.contains(title.trim().toUpperCase()+year.trim()+director.trim().toUpperCase()))
					continue;
				
				if (!MoviesQuery.contains("('"+title+"',"+year+",'"+director+"',null,null),"))
					MoviesQuery = MoviesQuery+"('"+title+"',"+year+",'"+director+"',null,null),";
			
				else
					continue;
				
				for (int i=0;i<genre.size();i++)
				{
					count1+=1;
				
					if(genres.contains(genre.get(i).toString().trim().toUpperCase()))
						continue;
					
					else if(!genres.contains(genre.get(i).toString().trim().toUpperCase()) && !genre.get(i).trim().equals(""))
					{
						genres.add(genre.get(i).toString().trim().toUpperCase());
						GenreQuery = GenreQuery+"('"+genre.get(i)+"'),";
					}
					
					if (count1%20 == 0)
					{
						GenreQuery = GenreQuery.substring(0,(GenreQuery.length()-1));
						System.out.println("Genres table updated for the movie "+title+". Rows added: "+statement.executeUpdate(GenreQuery));
						GenreQuery = "Insert into genres (name) values";
						result = statement.executeQuery("select upper(trim(name)) from genres");
						genres.clear();
						while(result.next())
							genres.add(result.getString(1));
						result.close();
					}
					
				}
				
				if (count%20 == 0)
				{
					MoviesQuery = MoviesQuery.substring(0,(MoviesQuery.length()-1));
					System.out.println("Insert query: "+MoviesQuery);
					System.out.println("Rows added: "+statement.executeUpdate(MoviesQuery));
					//MoviesQuery = "Insert into movies (title, year, director, banner_url, trailer_url) values";
					movielist.clear();
					result = statement.executeQuery("Select upper(trim(title)),year,upper(trim(director)) from movies");
					while (result.next())
						movielist.add(result.getString(1)+result.getString(2)+result.getString(3));
					result.close();
				}
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
		    	System.out.println("Type Error: "+ex.getMessage());
		    }
		
		}
		
		count=0;
		int movie_id;
		for (MovieXML m:Movie_ls)
		{
			try
			{
				
				if (count%20 == 0)
					GenreinMoviesQuery = "Insert into genres_in_movies (genre_id, movie_id) values";
				count+=1;
				genre = m.cat;
				//System.out.println("Movies select: select id from movies where title='"+m.getTitle().replace("'", "")+"'");
				result = statement.executeQuery("select id from movies where title='"+m.getTitle().replace("'", "")+"'");
			
				if (result.next())
					movie_id = result.getInt(1);
				else 
					continue;
				result.close();
			
				for(int i=0;i<genre.size();i++)
				{
					//System.out.println("Genre Select: select id from genres where name='"+genre.get(i)+"'");
					result = statement.executeQuery("select id from genres where name='"+genre.get(i)+"'");
					while(result.next())
						GenreinMoviesQuery = GenreinMoviesQuery+"("+result.getInt(1)+","+movie_id+"),";
				}
			
				if(count%20 == 0)
				{
					GenreinMoviesQuery = GenreinMoviesQuery.substring(0,(GenreinMoviesQuery.length()-1));
					System.out.println("GenreinMoviesQuery: "+GenreinMoviesQuery);
					System.out.println("Genre in movies table updated: "+statement.executeUpdate(GenreinMoviesQuery));
					//GenreinMoviesQuery = "Insert into genres_in_movies (genre_id, movie_id) values";
				}
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
		    	System.out.println("Type error: "+ex.getMessage());
		    }
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		
		MovieXMLParse spe = new MovieXMLParse();
		spe.runExample();
		spe.ParserLoad();
	
	}
	
}