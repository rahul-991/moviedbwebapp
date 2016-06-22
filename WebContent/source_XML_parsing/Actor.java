
public class Actor {

    private String familyname;
    private String firstname;
    private String stagename;
    private String  dob;
    
    public Actor(){
		
	}
	
	
    public String getFamilyName() {
        return familyname;
    }
    public void setFamilyName(String familyname) {
        this.familyname = familyname.trim();
    }
    
    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String firstname) {
        this.firstname = firstname.trim();
    }
    
    public String getStageName() {
        return stagename;
    }
    public void setStageName(String stagename) {
        this.stagename = stagename.trim();
    }
    
    public String getDob() {
        return dob;
    }
    
    public void setDob(int tempVal) {
        if (tempVal == 0)
        	this.dob = null; 
        else
        	this.dob = Integer.toString(tempVal);
    }
    
	public String Output() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("Actor Details - ");
		sb.append("FamilyName:" + getFamilyName());
		sb.append(", ");
		sb.append("FirstName:" + getFirstName());
		sb.append(", ");
		sb.append("DOB:" + getDob());
		sb.append(".");
		
		return sb.toString();
	}
}

