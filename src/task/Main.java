package task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
	
		String htmlText;
		ArrayList<String> arrayOfLinks;
		
		try {
			htmlText = NetworkService.getStringFromURL("https://www.ringtv.com/", "UTF-8");
			arrayOfLinks = NetworkService.getLinksFromHtml(htmlText);
			NetworkService.SaveLinksToFile(arrayOfLinks);			
		} catch (IOException e) {
			e.printStackTrace();
		}

		File fileWithListOfResourcesCsv = new File("Resources.csv");
		NetworkService.checkResourceAvailability(fileWithListOfResourcesCsv);
				
	}
	
	
}