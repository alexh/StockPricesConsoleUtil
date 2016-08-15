import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

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
	
	
	private static void PrintStocks(String file){
		String baseURL = "http://finance.google.com/finance/info?client=ig&q=";
		List<String> stocks = null;
		try {
			stocks = WatchlistReader.ReadWatchlist(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.print(ANSI_WHITE);
		System.out.println("| Ticker  | Price \t | Change \t | Percent Change | Time \t\t|");
		for(String stock : stocks){
			System.out.println("---------------------------------------------------------------------------------");
			 JSONObject json = null;
				try {
					json = GoogleFinanceAPIJsonReader.readJsonFromUrl(baseURL + stock);
				} catch (JSONException e) {
					System.out.println("Error in fetching data for " + stock + ". Did you spell it correctly?");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Error reading watchlist. Make sure the file exists.");
					e.printStackTrace();
				}
				   
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
				    
				    System.out.print("| " + ticker + "\t");
				    if (changeType == 1){
				    	System.out.print(ANSI_GREEN);
				    }
				    if (changeType == -1){
				    	System.out.print(ANSI_RED);
				    }
				    
				    System.out.print("| " + price + " \t ");
				    System.out.print("| " + change + " \t ");
				    System.out.print("| " + changePercent + "% \t  ");
				    System.out.print(ANSI_WHITE);
				    System.out.println("| " + time + " \t|");
		}
		
		System.out.println(ANSI_RESET);
	}
	
	public static void main(String[] args) {
		
		String input = "stocks";
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		while (!input.equals("quit")){
			System.out.println("Type 'quit' to quit, Type 'add TICK' to add a stock, Type 'stocks' to see stock prices, Type 'stocks 10 20' to refresh prices every 10 seconds for 20 seconds");
			input = reader.nextLine(); 
			if (input.equals("stocks")){
				PrintStocks(args[0]);
			}
			if (input.substring(0, 3).contains("add")){
				try
				{
				    FileWriter fw = new FileWriter(args[0],true); //the true will append the new data
				    fw.write("," + input.substring(4));//appends the string to the file
				    fw.close();
				}
				catch(IOException ioe)
				{
				   System.out.println("Couldn't add");
				}
				System.out.println("Current Watchlist: ");
				try {
					List<String> stocks = WatchlistReader.ReadWatchlist(args[0]);
					for (String s : stocks){
						System.out.print(s + ",");
					}
				} catch (FileNotFoundException e) {
				}
				System.out.println("");
			}
		}
		reader.close();
		
		
	}

}
