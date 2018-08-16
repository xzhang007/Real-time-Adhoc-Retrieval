import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class GetRelevantDOCNO {
	private static final List<String> relevantDOCNOs = new ArrayList<>();
	private static final int MAX = 10;
	
	public static void getRelevantDOCNO(int queryNo, String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("" + queryNo + " Q0 (\\d+) \\d+ .*");
		String docNo = null;
		//int nextQueryNo = queryNo + 1;
		int index = 0;
		
		try {
			fr =new FileReader(fileName);
			br = new BufferedReader(fr);
			
			String line = null;
			while (true) {
				line = br.readLine();
				if (line == null || index >= MAX) {  // end of file or has finished the top MAX
					break;
				}
				if (!line.matches("" + queryNo + " Q0 (\\d+) \\d+ .*")) { // query less than queryNo
					continue;
				}
				index++;
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					docNo = matcher.group(1);
					relevantDOCNOs.add(docNo);
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
	}
	
	public static List<String> getRelevantDOCNOs() {
		/*for (String docNo : relevantDOCNOs) {
			System.out.println(docNo);
		}*/
		return relevantDOCNOs;
	}
	
	public static void clearList() {
		relevantDOCNOs.clear();
	}
}
