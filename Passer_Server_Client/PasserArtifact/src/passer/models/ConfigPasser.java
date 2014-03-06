package passer.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigPasser {
	public static String SERVER_ADD="http://10.0.2.2:8080/PasserArtifact"; 
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (sdf.format(new Date()));
	}

}
