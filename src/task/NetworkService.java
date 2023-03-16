package task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NetworkService {
	
	public static String getStringFromURL(String spec, String code) throws IOException {
		
		URL url = new URL(spec);
		URLConnection connection = url.openConnection();
		String result = "";
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), code))) {
			String temp = "";
			for (;;) {
				temp = br.readLine();
				if (temp == null) {
					break;
				}
				result += temp + System.lineSeparator();
			}
			return result;
		}
	
	}
	
	public static ArrayList<String> getLinksFromHtml(String htmlText) {
		
		ArrayList<String> arrayOfLinks = new ArrayList<>();
		String stringOfLink;
		int indexOfHrefStart;
		int indexOfHrefEnd;
		
		String[] arrayOfStrings = htmlText.split(System.lineSeparator());
		
		for (int i = 0; i < arrayOfStrings.length; i++) {
			
			indexOfHrefStart = arrayOfStrings[i].indexOf("href=\"");
			
			if (indexOfHrefStart != -1) {
				indexOfHrefEnd = arrayOfStrings[i].indexOf("\"", indexOfHrefStart + 6);
				stringOfLink = arrayOfStrings[i].substring(indexOfHrefStart + 6, indexOfHrefEnd);
				if (! stringOfLink.equals("#")) {
					arrayOfLinks.add(arrayOfStrings[i].substring(indexOfHrefStart + 6, indexOfHrefEnd));	
				}
			}
					
		}
				
		return arrayOfLinks;
		
	}
	
	public static void SaveLinksToFile(ArrayList<String> arrayOfLinks) {

		File fileCsv = new File("Array of links.csv");
		
		try(PrintWriter pw = new PrintWriter(fileCsv)){
			for (String link : arrayOfLinks) {
				pw.println(link);	
			}
			System.out.println("Links were saved to csv file -> " + fileCsv.getAbsolutePath());
		}
		 catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void checkResourceAvailability(File fileWithListOfResourcesCsv) {
		
		try (Scanner sc = new Scanner(fileWithListOfResourcesCsv)) {
			String resourceSpec;
			for (; sc.hasNextLine();) {
				resourceSpec = sc.nextLine();
				try {
					Map<String, List<String>> headers = getHeadersFromURL(resourceSpec);
					if(headers.size() > 0) {
						System.out.println("Resource " + resourceSpec + " is Ok");
					} else {
						System.out.println("Bad resource " + resourceSpec);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
	}
	
	public static Map<String, List<String>> getHeadersFromURL(String spec) throws IOException{
		
	    URL url = new URL(spec);
	    URLConnection connection = url.openConnection();
	    return connection.getHeaderFields();
	    
	 }
	
}
