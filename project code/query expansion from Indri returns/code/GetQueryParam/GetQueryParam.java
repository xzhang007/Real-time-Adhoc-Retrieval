import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetQueryParam {
	public static void getQueryParam(String inputFileName, String outputFileName) {
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(inputFileName);
			br = new BufferedReader(fr);
			fw = new FileWriter(outputFileName);
			bw = new BufferedWriter(fw);
			
			writeHeadOfParam(bw);
			int queryNum = 0;
			String query = null;
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.regionMatches(0, "<number>", 0, 8)) {
				// or if (Pattern.matches("<num> Number: MB(\\d+) </num>", line)) {
					queryNum = getQueryNum(line);
					query = br.readLine();
					writeQueryParam(bw, queryNum, query);
				} 
			}
			writeEndOfParam(bw);  // important, don't forget
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
	
	private static void writeHeadOfParam(BufferedWriter bw) throws IOException {
		String[] lineArray = {"<parameters>",
				              "	<index>../termProject/output</index>",
				              "	<count>100</count>",
				              "	<trecFormat>true</trecFormat>"
							};
		for (String line : lineArray) {
			bw.write(line);
			bw.newLine();
		}
		bw.newLine();
	}
	
	private static int getQueryNum(String line) {
		int result = 0;
		Pattern pattern = Pattern.compile("<number>(\\d+)</number>");
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			result = Integer.parseInt(matcher.group(1));
		}
		
		return result;
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
	
	private static void writeQueryParam(BufferedWriter bw, int queryNum, String query)
		throws IOException {
		String[] lineArray = {"	<query>",
							  "		<type>indri</type>",
							  "		<number>" + queryNum + "</number>",
							  "		<text>" + query + "</text>",
							  "	</query>"
							 };
		for (String line : lineArray) {
			bw.write(line);
			bw.newLine();
		}
	}
	
	private static void writeEndOfParam(BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.write("</parameters>");
	}
}
