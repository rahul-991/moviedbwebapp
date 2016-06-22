import java.util.ArrayList;

public class MovieXML {
	

		private String title,year,director,fid;
		public ArrayList cat;
		
		
		public MovieXML()
		{
			cat = new ArrayList();
		}
		
		
		public void setFid(String f)
		{
			this.fid = f;
		}
		
		public String getFid()
		{
			if (fid!=null)
				return fid.trim();
			else
				return "";
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}
		public String getDirector() {
			return director;
		}

		public void setDirector(String director) {
			this.director = director;
		}
		
		public void AddCategory(String c) {
			this.cat.add(c);
		}

		
		public String Output() {
			StringBuffer sb = new StringBuffer();
			sb.append("Movie Details - ");
			sb.append("Title:" + getTitle());
			sb.append(", ");
			sb.append("Year:" + getYear());
			sb.append(", ");
			sb.append("Director:" + getDirector());
			sb.append(", ");
			sb.append("Categories:" );
			for(int i=0;i<cat.size();i++)
				sb.append(cat.get(i)+",");
			sb.append(".");
			
			return sb.toString();
		}
	}

