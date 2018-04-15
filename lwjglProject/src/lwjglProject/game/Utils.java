package lwjglProject.game;

import java.io.InputStream;
import java.util.Scanner;


public class Utils {

	public static String loadResorce(String filename) throws Exception {
		
		String result;
		
		try ( 
				InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(filename);
				Scanner scanner = new Scanner(in, "UTF-8")
		) {	
			result = scanner.useDelimiter("\\A").next();
		}
		
		
		return result;
		
	}
	
}
