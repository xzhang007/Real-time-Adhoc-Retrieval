import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetStatisticsOfRel {
	private static final Pattern pattern = Pattern.compile("rel/relFor\\d+ (\\d+)");
	private static final int MEAN = 12;
	
	public static void main(String[] args) {
		FileReader fr = null;
		BufferedReader br = null;
		int total = 0;
		int docNumBelow = 0;
		int docNumEqual = 0;
		
		try {
			fr = new FileReader("CountRel");
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int counter = Integer.parseInt(matcher.group(1));
					total += counter;
					docNumBelow = counter != 0 && counter <= MEAN ? docNumBelow + 1 : docNumBelow;
					docNumEqual = counter == MEAN ? docNumEqual + 1 : docNumEqual;
				}
			}
			
			System.out.println(total);
			System.out.println(docNumBelow);
			System.out.println(docNumEqual);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
