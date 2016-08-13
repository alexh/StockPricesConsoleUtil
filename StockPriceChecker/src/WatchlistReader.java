import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WatchlistReader {

	public static List<String> ReadWatchlist(String fileName) throws FileNotFoundException{
		List<String> toReturn = new LinkedList<>();
		 Scanner scanner = new Scanner(new File(fileName));
	        scanner.useDelimiter(",");
	        while(scanner.hasNext()){
	            toReturn.add(scanner.next());
	        }
	        scanner.close();
			return toReturn;
	}
	
}
