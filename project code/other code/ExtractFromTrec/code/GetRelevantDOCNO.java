import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class GetRelevantDOCNO {
	private static final List<String> relevantDOCNOs = new ArrayList<>();
	
	public static void getRelevantDOCNO(int queryNo, String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		Pattern pattern = Pattern.compile("" + queryNo + " Q*0 (\\d+) 1");
		String docNo = null;
		int nextQueryNo = queryNo + 1;
		
		try {
			fr =new FileReader(fileName);
			br = new BufferedReader(fr);
			
			String line = null;
			while (true) {
				line = br.readLine();
				if (line == null || line.matches("" + nextQueryNo + " Q*0 (\\d+) 1")) {  // end of file or query larger than queryNo
					break;
				}
				if (!line.matches("" + queryNo + " Q*0 (\\d+) 1")) { // query less than queryNo
					continue;
				}
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
