import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FilreqOnXML {
	private static final Pattern pattern = Pattern.compile("\\s+<text>(.*)</text>");
	
	public static void addFilreqOnXML(String fileName, String outputFile, long[] queryTweetTime) {
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		int qryIndex = 1;
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (!line.matches("\\s+<text>(.*)</text>")) {
					bw.write(line);
					bw.newLine();
					continue;
				}
				// <text>(.*)</text>
				long qryTweetTime = queryTweetTime[qryIndex++];
				addFileOnXML(bw, line, qryTweetTime);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void addFileOnXML(BufferedWriter bw, String line, long qryTweetTime) throws IOException{
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			String query = matcher.group(1);
			String newQuery = "<text>#filreq(#less(requested_id " + qryTweetTime + ") #combine(" + query + "))</text>";
			bw.write(newQuery);
			bw.newLine();
		}
	}
}
