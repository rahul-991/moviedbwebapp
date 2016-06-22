import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CastsSaxHandler extends DefaultHandler implements ErrorHandler 
{

	boolean bDirfilms = false;
	boolean bDirid = false;
	boolean bIs = false;
	boolean bCastnote = false;

	boolean bFilmc = false;
	boolean bM = false;
	boolean bF = false;
	boolean bT = false;
	
	boolean bA = false;
	boolean bP = false;
	boolean bR = false;
	boolean bRname = false;

	public void warning(SAXParseException e) throws SAXException 
	{
		System.out.println("WARNING : " + e.getMessage()); // do nothing
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

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		if (qName.equalsIgnoreCase("dirfilms")) 
			bDirfilms = true;
		
		else if (qName.equalsIgnoreCase("dirid")) 
			bDirid = true;
		
		else if (qName.equalsIgnoreCase("is"))
			bIs = true;
		
		else if (qName.equalsIgnoreCase("castnote"))
			bCastnote = true;
	
		else if (qName.equalsIgnoreCase("filmc"))
			bFilmc = true;
		
		else if (qName.equalsIgnoreCase("m"))
			bM = true;
		
		else if (qName.equalsIgnoreCase("f"))
			bF = true;
		
		else if (qName.equalsIgnoreCase("t"))
			bT = true;
		
		else if (qName.equalsIgnoreCase("a"))
			bA = true;
		
		else if (qName.equalsIgnoreCase("p"))
			bP = true;
		
		else if (qName.equalsIgnoreCase("r"))
			bR = true;
		
		else if (qName.equalsIgnoreCase("rname"))
			bRname = true;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		
		if (qName.equalsIgnoreCase("casts"))
			System.out.println("--------------------------");
		
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException 
	{
		if (bDirfilms)
		{
			System.out.println("Dirfilms: " + new String(ch, start, length));
			bDirfilms = false;
		}
		
		else if (bDirid)
		{
			System.out.println("Dirid: " + new String(ch, start, length));
			bDirid = false;
		}
		
		else if (bIs)
		{	
			System.out.println("Is: " + new String(ch, start, length));
			bIs = false;
		}
		
		else if (bCastnote)
		{
			System.out.println("Castnote: " + new String(ch, start, length));
			bCastnote = false;
		}
		
		else if (bFilmc)
		{
			System.out.println("Filmc: " + new String(ch, start, length));
			bFilmc = false;
		}
		
		else if (bM)
		{
			System.out.println("M: " + new String(ch, start, length));
			bM = false;
		}
		
		else if (bF)
		{
			System.out.println("F: " + new String(ch, start, length));
			bF = false;
		}
		
		else if (bT)
		{
			System.out.println("T: " + new String(ch, start, length));
			bT = false;
		}
		
		else if (bA)
		{
			System.out.println("A: " + new String(ch, start, length));
			bA = false;
		}
		
		else if (bP)
		{
			System.out.println("P: " + new String(ch, start, length));
			bP = false;
		}
		
		else if (bR)
		{
			System.out.println("R: " + new String(ch, start, length));
			bR = false;
		}
		
		else if (bRname)
		{
			System.out.println("Rname: " + new String(ch, start, length));
			bRname = false;
		}
	}
}
