import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetQueryTweetTime {
	private static final int TOTAL = 110;
	private static long[] queryTweetTime = new long[TOTAL + 1];
	private static final Pattern pattern = Pattern.compile("<querytweettime> (\\d+) </querytweettime>");
	
	public static long[] getQueryTweetTime() {
		FileReader fr = null;
		BufferedReader br = null;
		int index = 1;
		
		try {
			fr = new FileReader("queries.txt");
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.matches("<querytweettime> \\d+ </querytweettime>")) {
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						long qryTweetTime = Long.parseLong(matcher.group(1));
						queryTweetTime[index++] = qryTweetTime;
					}
				}
			}
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
		
		return queryTweetTime;
	}
}
