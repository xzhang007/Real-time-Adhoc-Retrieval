import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* This class is to get the sum of document frequence for a given term from the 7 files in inverted index 1.
*/

public class GetRawDf {
	public static int traverseDfs(String dirName, String term) {
		int totalDf = 0;
		File dir = new File(dirName);
		for (String file : dir.list()) {
			if (!file.matches("DocFreq\\d+")) {
				continue;
			}
			totalDf += getRawDf(dirName + "/" + file, term);
		}
		return totalDf;
	}
	
	private static int getRawDf(String fileName, String term) {
		FileReader fr = null;
		BufferedReader br = null;
		int df = 0;
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.matches(term + "		\\d+")) {
					//System.out.println("000");
					Pattern pattern = Pattern.compile(term + "		(\\d+)");
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						df = Integer.parseInt(matcher.group(1));
					}
					break;
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
		
		//System.out.println(term + " " + fileName + " " + df);
		return df;
	}
}
