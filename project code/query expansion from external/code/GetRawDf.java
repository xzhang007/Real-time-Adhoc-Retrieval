import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetRawDf {
	public static int getRawDf(int qryId, String term) {
		FileReader fr = null;
		BufferedReader br = null;
		int df = 0;
		
		try {
			fr = new FileReader("relMap/df" + qryId);
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.matches(term + " \\d+" )) {
					Pattern pattern = Pattern.compile(term + " (\\d+)");
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
