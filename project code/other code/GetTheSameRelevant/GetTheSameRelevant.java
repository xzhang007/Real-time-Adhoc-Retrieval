import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetTheSameRelevant {
	private static final Pattern pattern = Pattern.compile("(\\d+) Q0 (\\d+) \\d+ -\\d+.\\d+ indri");
	
	public static void getTheSameRelevant(String fileName1, String fileName2, String outputFile) {
		FileReader fr1 = null, fr2 = null;
		BufferedReader br1 = null, br2 = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr1 = new FileReader(fileName1);
			br1 = new BufferedReader(fr1);
			
			fw = new FileWriter(outputFile, true);  // append
			bw = new BufferedWriter(fw);
			
			while (true) {
				String line1 = br1.readLine();
				if (line1 == null) {
					break;
				}
				Matcher matcher = pattern.matcher(line1);
				if (matcher.matches()) {
					int queryNo = Integer.parseInt(matcher.group(1));
					String relDoc = matcher.group(2);
					
					fr2 = new FileReader(fileName2);
					br2 = new BufferedReader(fr2);
					while (true) {
						String line2 = br2.readLine();
						if (line2 == null) {
							break;
						}
						if (line2.matches("" + queryNo + " Q*0 " + relDoc + " 1")) {
							bw.write(line1);
							bw.newLine();
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br1.close();
				br2.close();
				fr1.close();
				fr2.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
