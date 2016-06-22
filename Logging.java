import java.io.*;

public class Logging {

	public static void main(String[] args) {
		 String fileName = "catalina.out";

	      int i=0;
	        String line = null;
            long sum1= 0;
            long sum2= 0;
	        try {
	           
	            FileReader fileReader = new FileReader(fileName);
	            
	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	              String arr[] = line.split(" ");
	              
	             long a= Long.parseLong(arr[1]);
	             long b= Long.parseLong(arr[3]);
	             i++;
	             sum1 += a;
	             sum2 += b;
	            } 
                 
	            long avg1= sum1/i;
	            long avg2= sum2/i;
	            
	           
	          
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");                  
	           
	        }
	    }

}
