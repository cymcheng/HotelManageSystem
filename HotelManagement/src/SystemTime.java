import java.util.Date;
import java.text.SimpleDateFormat;

public class SystemTime {
	
	public static String SystemTimeNow() {
		
	    StringBuilder string_SystemTime = new StringBuilder();  
	    string_SystemTime.append("yyyy-MM-dd"); 
	    SimpleDateFormat date_SystemTime = new SimpleDateFormat(string_SystemTime.toString());  
	    String systemTime = date_SystemTime.format(new Date());   
		return systemTime;
	
	}
}

