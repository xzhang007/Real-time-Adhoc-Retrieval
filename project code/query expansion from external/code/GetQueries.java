import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetQueries {
	private static final int TOTAL = 110;
	private static String[] originalQueries = new String[TOTAL + 1]; // for index from 1 to 110
	
	public static String[] getQueries(String inputFileName) {
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(inputFileName);
			br = new BufferedReader(fr);
			
			int queryNum = 1;
			String query = null;
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.regionMatches(0, "<title>", 0, 7) || line.regionMatches(0, "<query>", 0, 7)) {
					query = dealWithQuery(getQuery(line));
				} else {
					continue;
				}
				originalQueries[queryNum++] = query;
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
		return originalQueries;
	}
	
	private static String getQuery(String line) {
		String query = null;
		Pattern pattern = Pattern.compile("<\\w+> (.*) </\\w+>");
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			query = matcher.group(1);
		}
		
		return query;
	}
	
	/**
	 *  private method to remove some punctuations, such as "" , ' 's.
	 *  @param query
	 *  @return the new query
	*/
	private static String dealWithQuery(String query) {
		String result = query;
		// remove ""
		Pattern pattern = Pattern.compile("(.*)\"(.*)\"(.*)");
		Matcher matcher = pattern.matcher(query);
		if (matcher.matches()) {
			result = matcher.group(1) + "#1(" + matcher.group(2) + ")" + matcher.group(3);
		}
		// remove ,
		pattern = Pattern.compile("(.*),(.*)");
		matcher = pattern.matcher(query);
		if (matcher.matches()) {
			result = matcher.group(1) + matcher.group(2);
		}
		// remove ' or 's
		pattern = Pattern.compile("(.*)'s*(.*)");
		matcher = pattern.matcher(query);
		if (matcher.matches()) {
			result = matcher.group(1) + matcher.group(2);
		}
		
		return result;
	}
}
