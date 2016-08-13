import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
public class Main {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void main(String[] args) {
		String baseURL = "http://finance.google.com/finance/info?client=ig&q=";
		
		List<String> stocks = null;
		try {
			stocks = WatchlistReader.ReadWatchlist(args[0]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String stock : stocks){
			 JSONObject json = null;
				try {
					json = GoogleFinanceAPIJsonReader.readJsonFromUrl(baseURL + stock);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   System.out.print(ANSI_WHITE);
				    String ticker = json.getString("t");
				    String price = json.getString("l");
				    String change = json.getString("c");
				    String changePercent = json.getString("cp");
				    String time = json.getString("lt");
				    int changeType = 0;
				    if (change.contains("+")){
				    	changeType = 1;
				    }
				    if (change.contains("-")){
				    	changeType = -1;
				    }
				    
				    System.out.println(ticker);
				    if (changeType == 1){
				    	System.out.print(ANSI_GREEN);
				    }
				    if (changeType == -1){
				    	System.out.print(ANSI_RED);
				    }
				    System.out.println(price);
				    System.out.println(change);
				    System.out.println(changePercent);
				    System.out.print(ANSI_WHITE);
				    System.out.println(time);
		}
		
		System.out.println(ANSI_RESET);
		
	}

}
